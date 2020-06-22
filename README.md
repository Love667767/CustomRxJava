# CustomRxJava
实现一个简易版的 RxJava


1. 配置需要上传 aar 的 Module 中的 build.gradle 文件，相关的参数信息配置在 gradle.properties 中。
```java
apply plugin: 'maven'

uploadArchives {
    repositories.mavenDeployer {
        pom.groupId = GROUP_ID
        pom.artifactId = ARTIFACT_ID
        pom.version = VERSION
        pom.packaging = TYPE

        if (uploadRemoteMaven) {
            // Maven私服
            repository(url: MAVEN_REPO_RELEASE_URL) {
                authentication(userName: NEXUS_USERNAME, password: NEXUS_PASSWORD)
            }
            snapshotRepository(url: MAVEN_REPO_SNAPSHOT_URL) {
                authentication(userName: NEXUS_USERNAME, password: NEXUS_PASSWORD)
            }
        } else {
            // 配置本地仓库路径
            // repository(url: uri('../repository'))
            snapshotRepository(url: uri('../repository-snapshot'))
        }
    }
}
```

2. gradle.properties 文件
```java
#Maven仓库 URL
MAVEN_REPO_RELEASE_URL=http://localhost:8081/nexus/content/repositories/releases/
MAVEN_REPO_SNAPSHOT_URL=http://localhost:8081/nexus/content/repositories/snapshots/

#登录nexus ossde的用户名
NEXUS_USERNAME=elson
#登录nexus oss的密码
NEXUS_PASSWORD=123456

# 包信息：goupId:archives:
GROUP_ID = com.elson.customrxjava
ARTIFACT_ID = custom-rxjava
VERSION = 1.0.0-SNAPSHOT
#VERSION = 1.0.0
TYPE = aar
DESCRIPTION = Custom Rxjava Lib
```

3. 在根目录的 build.gradle 文件中配置依赖
```java
allprojects {
    repositories {
        google()
        jcenter()

        // Maven 私服
        maven { url 'http://127.0.0.1:8081/nexus/content/groups/public/' }
    }
}
```


4. 在需要使用的地方添加依赖
```java
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])

    // 添加依赖
    implementation 'com.elson.customrxjava:custom-rxjava:1.0.0-SNAPSHOT'
}
```
