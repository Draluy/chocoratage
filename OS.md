# Tested OSes

* **bold means** "valid locking solution"
 * *italic* means "partially working" or "unexpected behavior" 

| **Distro** | **Version** | **Variant** | **Inherits** | **XDG_CURRENT_DESKTOP** | **GDMSESSION** | **Java os.name os.version** | **/etc/os-release** | **lsb_release -i -r** | **/etc/fedora-release** | **uname -onr** | **dbus-send** | **xdg-screensaver lock** | **Robot** | **gnome-session-quit --no-prompt** | **Other** |
| --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- | --- |
| **Chakra** | 2017.10 | KDE Plasma | Arch | KDE | <empty> | Linux 4.13.11-1-CHAKRA | Chakra <empty> | Chakra 2017.02 | N/A | GNU/Linux localhost.localdomain 4.12.4-1-CHAKRA | NO | **YES** | **Ctrl+Alt+L** | N/A | - |
| **Debian** | 10 | | | X-Cinnamon | cinnamon | Linux 4.19.0-6-amd64 | **Debian GNU/Linux 10** | Debian 10 | N/A | GNU/Linux debian 4.19.0-6-amd64 | NO | **YES** | **Ctrl+Alt+L** | **YES** | - |
| **Elementary** | 5.1 | | Ubuntu | Pantheon | pantheon | Linux 5.0.0-37-generic | **elementary OS 5.1** | elementary 5.1 | N/A | GNU/Linux <vm name> 5.0.0-37-generic | NO | **YES** | NO (warnings + Failed to load module "canberra-gtk-module"), but locked once after a while | NO (still prompts for log out) | - |
| **Fedora** | 25 | Workstation | | GNOME | gnome | Linux 4.8.6-300.fc25.x86_64 | Fedora 25 | N/A | Fedora release 25 (Twenty Five) | GNU/Linux localhost.localdomain 4.8.6-300.fc25.x86_64 | NO | **YES** | NO | **YES** | - |
| **Fedora** | 31 | Workstation | | GNOME | gnome | Linux 5.3.7-301.fc31.x86_634 | **Fedora 31** | N/A | Fedora release 31 (Thirty One) | GNU/Linux localhost.localdomain 5.3.7-301.fc31.x86_634 | NO | **YES** | NO | **YES** | - |
| **FreeBSD** | 11.2 | | BSD | GNOME | gnome | **FreeBSD 11.2-RELEASE** | N/A | N/A | N/A | FreeBSD freebsd 11.2-RELEASE | NO | NO | NO | **YES** | - |
| **FreeBSD** | 12.1 | | BSD | GNOME | gnome | **FreeBSD 12.1-RELEASE** | N/A | N/A | N/A | FreeBSD freebsd 12.1-RELEASE | **YES** | NO | NO | **YES** | (if installed) xlock, xscreensaver-command |
| **Lubuntu** | 18.04 | LXDE | Ubuntu | LXDE | Lubuntu | Linux 5.0.0-23-generic | *Ubuntu 18.04* | *Ubuntu 18.04* | N/A | GNU/Linux <vm name> 5.0.0-23-generic | NO | **YES** | **Ctrl+Alt+L** | N/A | - |
| **Lubuntu** | 19.10 | LXQt | Ubuntu | LXQt | <empty> | Linux 5.3.0-18-generic | *Ubuntu 19.10* | *Ubuntu 19.10* | N/A | GNU/Linux <pc name> 5.3.0-18-generic | NO | **YES** | **Ctrl+Alt+L** | N/A | - |
| **Mageia** | 7.1 | KDE Plasma | Mandriva | KDE | <empty> | Linux 5.1.14-desktop-1.mga7 | Mageia 7 | Mageia 7 | N/A | GNU/Linux localhost 5.1.14-desktop-1.mga7 | NO | **YES** | **Ctrl+Alt+L** | NO | - |
| **Manjaro-i3** | 18.14 | i3 | Arch | i3 | i3 | Linux 5.4.2-1-MANJARO | Manjaro Linux <empty> | ManjaroLinux 18.1.4 | N/A | GNU/Linux mandjaro-i3 5.4.2-1-MANDJARO | | YES but slow | NO | NO | - |
| **Mint** | 19.2 | Cinnamon | Ubuntu | X-Cinnamon | cinnamon | Linux 4.15.0-54-generic | **Linux Mint 19.2** | LinuxMint 19.2 | N/A | GNU/Linux <vm name> 4.15.0-54-generic | NO | **YES** | **Ctrl+Alt+L** | **YES** | - |
| **Mint** | 19.2 | Mate | Ubuntu | MATE | mate | Linux 4.15.0-54-generic | **Linux Mint 19.2** | LinuxMint 19.2 | N/A | GNU/Linux <vm name> 4.15.0-54-generic | NO | **YES** | **Ctrl+Alt+L** | N/A | - |
| **Mint** | 19.2 | xfce | Ubuntu | **XFCE** | xfce | Linux 4.15.0-54-generic | **Linux Mint 19.2** | LinuxMint 19.2 | N/A | GNU/Linux <vm name> 4.15.0-54-generic | NO | **YES** | **Ctrl+Alt+L** | N/A | - |
| **OpenMLandriva Lx** | 4.0 (Nitrogen) | | Mandriva Linux | | | Linux 5.1.9-desktop-1omv4000 | OpenMandriva Lx 4.0 | OpenMandrivaLinux 4.0 | N/A | GNU/Linux <vm name> 5.1.9-desktop-1omv4000 | NO | **YES** | **Ctrl+Alt+L** | N/A | - |
| **OpenSuse** | LEAP 15.1 | | | KDE | <empty> | Linux 4.12.14-lp151.28.32-default | **openSUSE Leap 15.1** | openSUSE 15.1 | N/A | GNU/Linux linux-psei 4.12.14-lp151.28.32-default | NO | **YES** | **Ctrl+Alt+L** | N/A | - |
| **OSX** | 10.8.5 | Mountain Lion | BSD | <empty> | <empty> | Mac OSX 10.8.5 | N/A | N/A | N/A | <illegal options -o> <machine name>.local 12.5.0 -s gives Darwin | N/A | N/A | NO (Ctrl+Shit+Esc) | N/A | **/System/Library/CoreServices/Menu\ Extras/User.menu/Contents/Resources/CGSession -suspend** |
| **OSX** | 10.10.5 | Yosemite | BSD | <empty> | <empty> | 10.10.5 | N/A | N/A | N/A | <illegal options -o> <machine name>.local 14.5.0 -s gives Darwin | N/A | N/A | NO (Ctrl+Shit+Esc) | N/A | **/System/Library/CoreServices/Menu\ Extras/User.menu/Contents/Resources/CGSession -suspend** |
| **OSX** | 10.13.6 | High Sierra | BSD | <empty> | <empty> | 10.13.6 | N/A | N/A | N/A | <illegal options -o> <machine name>.local 17.7.0 -s gives Darwin | N/A | N/A | NO (Ctrl+Shit+Esc) | N/A | **/System/Library/CoreServices/Menu\ Extras/User.menu/Contents/Resources/CGSession -suspend** |
| **OSX** | 10.14.6 | Mojave | BSD | <empty> | <empty> | Mac OSX 10.14.6 | N/A | N/A | N/A | <illegal options -o> <machine name>.local 18.7.0 -s gives Darwin | N/A | N/A | NO (Ctrl+Shit+Esc) | N/A | **/System/Library/CoreServices/Menu\ Extras/user.menu/Contents/Resources/CGSession -suspend** |
| **OSX** | 10.15.2 | Catalina | BSD | <empty> | <empty> | Mac OXS 10.15.2 | N/A | N/A | N/A | <illegal options -o> <machine name>.local 19.2.0 -s gives Darwin | N/A | N/A | NO (Ctrl+Shit+Esc) | N/A | **/System/Library/CoreServices/Menu\ Extras/User.menu/Contents/Resources/CGSession -suspend** |
| **Solaris** | 10 | CDE | SVR4 | <empty> | <empty> | SunOS 5.10 | N/A | N/A | N/A | <illegal options -o> unknown 5.10 -s gives SunOS | N/A | N/A | NO | N/A | **xlock** |
| **Solaris** | 11.4 | Gnome | SVR4 | GNOME | gnome | SunOS 5.11 | Oracle Solaris 11.4 | N/A | N/A | solaris 5.11 Solaris | NO | *Hangs the whole WM* | NO | **YES, although the screen remains black for a while and gnome has a hard time getting back to work** | **xlock** |
| **Solaris** | 11.4 | xterm | SVR4 | | xterm | SunOS 5.11 | Oracle Solaris 11.4 | N/A | N/A | solaris 5.11 Solaris | NO | NO | NO | NO | **xlock** |
| **Ubuntu** | 14.10 | | | Unity | ubuntu | Linux 3.16.0-23-generic | Ubuntu 14.10 | Ubuntu 14.10 | N/A | GNU/Linux <vm name> 3.16.0-23-generic | NO | **YES** | **Ctrl+Alt+L** | **YES** | - |
| **Ubuntu** | 16.04 | Unity | | Unity | ubuntu | Linux 4.15.0-45-generic | **Ubuntu 16.04** | Ubuntu 16.04 | N/A | GNU/Linux <vm name> 4.15.0-45-generic | NO | **YES** | **Ctrl+Alt+L** | **YES** | - |
| **Ubuntu** | 17.04 | | | Unity:Unity7 | ubuntu | Linux 4.10.0-19-generic | Ubuntu 17.04 | Ubuntu 17.04 | N/A | GNU/Linux <vm name> 4.10.0-19-generic | NO | **YES** | **Ctrl+Alt+L** | **YES** | - |
| **Ubuntu** | 18.04 | | | ubuntu:GNOME | ubuntu | Linux 4.15.0-72-generic | **Ubuntu 18.04** | Ubuntu 18.04 | N/A | GNU/Linux <vm name> 4.15.0-72-generic | NO | **YES** | NO (Failed to load module "canberra-gtk-module") | *YES (but hangs the VM)* | - |
| **Ubuntu** | 19.10 | Budgie | Ubuntu | budgie:GNOME | budgie-desktop | Linux 5.3.0-24-generic | *Ubuntu 19.10* | *Ubuntu 19.10* | N/A | GNU/Linux <vm name> 5.3.0-24-generic | NO | **YES** | NO | *YES (but hangs the VM)* | - |
| **Windows** | 10 | 1903 | NT | N/A | N/A | **Windows 10 10.0** | N/A | N/A | N/A | N/A | N/A | N/A | N/A | N/A | **<windir>\System32\rundll32.exe user32.dll,LockWorkStation** |
| **Windows** | 7 SP1 | 7601 | NT | N/A | N/A | Windows 7 6.1 | N/A | N/A | N/A | N/A | N/A | N/A | N/A | N/A | **<windir>\System32\rundll32.exe user32.dll,LockWorkStation** |
| **Zorin** | 15.1 | Core | Ubuntu | zorin:GNOME | zorin | Linux 5.0.0-37-generic | **Zorin OS 15** | Zorin 15 | N/A | GNU/Linux <vm name> 5.0.0-37-generic | NO | **YES** | NO (Failed to load module "canberra-gtk-module") | **YES** | - |
| **Zorin** | 15.1 | Lite (xfce) | Ubuntu | **XFCE** | zorin-os-lite | Linux 5.0.0-37-generic | **Zorin OS 15** | Zorin 15 | N/A | GNU/Linux <vm name> 5.0.0-37-generic | NO | NO | **Ctrl+Alt+L** | NO | |
