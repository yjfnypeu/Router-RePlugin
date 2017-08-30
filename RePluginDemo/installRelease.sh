./gradlew clean assembleRelease
echo "build successful"
rm ./app/src/main/assets/plugins/plugina.jar
rm ./app/src/main/assets/plugins/usercenter.jar
echo "delete last build apk successful"
cp ./plugina/build/outputs/apk/plugina-release.apk ./app/src/main/assets/plugins/plugina.jar
cp ./usercenter/build/outputs/apk/usercenter-release.apk ./app/src/main/assets/plugins/usercenter.jar
echo "all successful"