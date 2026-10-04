#!/usr/bin/env bash
# DroidDeck 中文版: sign-zh.sh <in.apk> <out.apk>
#
# Signs a CI build (testkey) with this fork's own release key - v1 + v2 + v3, no lineage: the
# package is com.droiddeck.launcher.zh, a different app from the original, so there is nothing
# to rotate from. Every release signed with the same key installs over the last one.
#   RELEASE_KEYSTORE        path to the PKCS12 keystore (from the RELEASE_KEYSTORE_B64 secret)
#   RELEASE_STORE_PASSWORD  its password (store and key share it)
#   RELEASE_KEY_ALIAS       the key's alias (droiddeck)
#   BUILD_TOOLS             Android build-tools dir (zipalign, apksigner, aapt)
set -euo pipefail
die() { echo "::error::$*" >&2; exit 1; }
[ $# -eq 2 ] || die "usage: sign-zh.sh <in.apk> <out.apk>"
in=$1 out=$2
repo=$(cd "$(dirname "$0")/../.." && pwd)
expected=$(tr -d ' \n' < "$repo/keystore/release-signer.sha256")
want_pkg=$(sed -n 's/^ *applicationId "\(.*\)"$/\1/p' "$repo/app/build.gradle")
: "${RELEASE_KEYSTORE:?}" "${RELEASE_STORE_PASSWORD:?}" "${RELEASE_KEY_ALIAS:?}" "${BUILD_TOOLS:?}"
[ -s "$RELEASE_KEYSTORE" ] || die "the release keystore is empty - is RELEASE_KEYSTORE_B64 set?"
work=$(mktemp -d); trap 'rm -rf "$work"' EXIT

"$BUILD_TOOLS/zipalign" -p -f 4 "$in" "$work/aligned.apk"
"$BUILD_TOOLS/apksigner" sign \
  --ks "$RELEASE_KEYSTORE" --ks-type PKCS12 --ks-pass env:RELEASE_STORE_PASSWORD \
  --ks-key-alias "$RELEASE_KEY_ALIAS" --key-pass env:RELEASE_STORE_PASSWORD \
  --v1-signing-enabled true --v2-signing-enabled true --v3-signing-enabled true --v4-signing-enabled false \
  --out "$out" "$work/aligned.apk"
rm -f "$out.idsig"

"$BUILD_TOOLS/apksigner" verify --min-sdk-version 21 --verbose --print-certs "$out" | tee "$work/sig.txt"
for scheme in "v1 scheme (JAR signing): true" "v2 scheme (APK Signature Scheme v2): true" "v3 scheme (APK Signature Scheme v3): true"; do
  grep -qF "$scheme" "$work/sig.txt" || die "not signed with $scheme"
done
got=$(sed -n 's/^.*Signer.* certificate SHA-256 digest: //p' "$work/sig.txt" | sort -u)
[ "$got" = "$expected" ] || die "signed by [$got], not the release key $expected"
pkg=$("$BUILD_TOOLS/aapt" dump badging "$out" | sed -n "s/^package: name='\([^']*\)'.*/\1/p")
[ "$pkg" = "$want_pkg" ] || die "the apk says package '$pkg', not '$want_pkg'"
"$BUILD_TOOLS/aapt" dump badging "$out" | grep -E "^(package|application-label|application-label-zh-CN|sdkVersion|targetSdkVersion):"
echo "OK: $out is $pkg, signed by $got"
