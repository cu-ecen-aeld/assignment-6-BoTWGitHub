# See https://git.yoctoproject.org/poky/tree/meta/files/common-licenses
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

SRC_URI = "git://git@github.com/cu-ecen-aeld/assignments-3-and-later-BoTWGitHub;protocol=ssh;branch=master"

PV = "1.0+git${SRCPV}"
SRCREV = "08b5dacf26fdba4b0311e229ff3467455c740e9c"

S = "${WORKDIR}/git/server"

FILES:${PN} += "${bindir}/aesdsocket"

TARGET_LDFLAGS += "-pthread -lrt"

inherit update-rc.d
INITSCRIPT_PACKAGES="${PN}"
INITSCRIPT_NAME:${PN} = "aesd-server-init"

do_configure () {
	:
}

do_compile () {
	oe_runmake
}

do_install () {
	# TODO: Install your binaries/scripts here.
	# Be sure to install the target directory with install -d first
	# Yocto variables ${D} and ${S} are useful here, which you can read about at 
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-D
	# and
	# https://docs.yoctoproject.org/ref-manual/variables.html?highlight=workdir#term-S
	# See example at https://github.com/cu-ecen-aeld/ecen5013-yocto/blob/ecen5013-hello-world/meta-ecen5013/recipes-ecen5013/ecen5013-hello-world/ecen5013-hello-world_git.bb
	install -d ${D}${bindir} ${D}${sysconfdir}/init.d
	install -m 0755 ${S}/aesdsocket ${D}${bindir}/
	install -m 0755 ${S}/aesdsocket-start-stop.sh -T ${D}${sysconfdir}/init.d/aesd-server-init
}
