plugins {
    kotlin("multiplatform") version "1.8.0-Beta"
}

group = "me.miso"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

kotlin {
    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosArm64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    nativeTarget.apply {
        binaries {
            executable {
                entryPoint = "main"
            }
        }

        compilations["main"].cinterops {
            val ncurses by creating {

            }
        }
    }
    sourceSets {
        val nativeMain by getting
        val nativeTest by getting
    }
}
