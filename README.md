# Chocoratage

## Pourquoi ?

Si vous laissez votre ordinateur sans surveillance et sans le verrouiller, il y a de fortes chances que quelqu'un envoie un message ou un e-mail à toute l'entreprise disant que vous apporterez le petit-déjeuner pour tout le monde le lendemain. Cela s'appelle un ** "chocoblast" **!  
Il y a même [un site](https://www.chocoblast.fr/) qui lui est dédié.

La raison invoquée pour cette pratique est d'améliorer la securité en donnant l'habitude aux développeurs de  verrouiler leurs postes.
Or, ce raisonnement est problématique:

* Il rabaisse les développeurs a des [chiens de pavlov](https://fr.wikipedia.org/wiki/Conditionnement_classique), en partant du principe que parler à leur intellect s'avèrera infructueux.
* Il part du principe que l'environnement de travail est hostile. Or, si c'est le cas, c'est l'environnement qui est problématique, et c'est lui qui doit être changé. 
* Il donne la délation comme modèle de fonctionnement, et la récompense. Cela ne favorise pas la cohésion d'équipe. Comment espérer que des collègues qui se tirent dans les pattes toute l'année fassent preuve de cohésion lorsqu'un problème de production se présentera?

Un comportement adapté est le suivant: surveiller le PC de nos collègues lorsqu'ils s'absentent momentanément, comme ils le feront pour nous; et verrouiller le poste qui doit l'être, en avertissant gentiment le fautif. C'est une manière plus positive de gérer le même problème.

Donc nous contre-attaquons le _"chocoblastage"_ avec ce projet: _"chocoratage"_.

## Comment ça marche

Chocoratage est écrit en Java 8. It embarque une liste de mots-clés et bouts de phrases sur le thème du petit déjeuner. Il détecte votre OS au démarrage et écoute votre clavier à l'aide d'un keylogger très léger. Lorsque quelqu'un tape une suite de caractères approchante d'un des termes interdits, il vérouille automatiquement votre session.     
Le programme utilise un algo de Levenshtein pour la reocnnaissance des termes interdits.  
Ca ne rendra pas votre ordinateur totalement imperméable au chocoblastage, vos collègues pouvant devenir créatifs quand ils ont faim, néanmoins cela vous sauvera bien la mise une ou deux fois.

## Installation

Téléchargez le uberjar, et lancez-le simplement avec `java -jar chocoratage.jar`  
Vous pouvez éventuellement utiliser le .bat ou .sh que vous trouverez dans /scripts.
C'est tout.

## Options de ligne de commande
- **h/help** : l'aide :)
- **d/debug** : trace les évènements du programme sur la console
- **f/forbidden**=&lt;path&gt; : fournit une liste de termes interdits, un par ligne, pouvant comporter plusieurs mots chacun, pour remplacer celui par défaut. 
- **l/lock** : verrouille la session au démarrage
- **o/os**=&lt;os&gt; : / force l'os, valeurs aurorisées : LINUX, WINDOWS, OSX, FREEBSD, SOLARIS, AIX, OTHER_UNIX, UNKNOWN, bien que seuls les 5 premiers soient câblés pour permettre le verrouillage d'une session.  
- **strict**=&lt;true/false&gt; : fait que la reconnaissance est stricte, faux par défaut. 
- **s/simu** : loggue au lieu de vérouiller la session.

## Compatibilité
Nous avons testé et câblé un vérouillage efficace sur des VMs ou du matériel faisant tourner les OS et distributions suivants:

### Linux
- Chakra 2017.10
- Debian 10
- Elementary 5.1
- Fedora 25
- Fedora 31
- Lubuntu 18.04 LXDE
- Lubuntu 19.10 LXQt
- Mageia 7.1
- Manjaro-i3 18.14
- Mint 19.2 (Cinnamon, Mate, xfce)
- OpenMLandriva Lx 4.0 (Nitrogen)
- OpenSuse LEAP 15.1
- Ubuntu Utopic Unicorn 14.10 Unity
- Ubuntu Xenial Xerus 16.04 LTS Unity
- Ubuntu Zesty Zapus 17.04 Unity
- Ubuntu Bionic Beaver 18.04 LTS Gnome
- Ubuntu Eoan Ermine 19.10 Budgie
- Zorin 15.1 Core & Lite (xfce)

### Windows
- Windows 10
- Windows 7 SP1

### OSX
- OSX Mountain Lion 10.8.5
- OSX Yosemite 10.10.5
- OSX High Sierra 10.13.6
- OSX Mojave 10.14.6
- OSX Catalina 10.15.2

### Solaris
- Solaris 10
- Solaris 11.4 (Gnome & xterm)

* De toute évidence, de nombreuses autres distributions sont également compatibles (ex:, d'autres Fedora, Debian, Windows 8, OSX Mavericks, El Capitan, etc ...).
* Cela dit, OSX Catalina a une politique de sécurité horrible qui rendra même l'exécution de java difficile. Nous n'y pouvons pas grand-chose...
* D'ailleurs, pour que l'écran de verrouillage vous protège réellement sur OSX, vous devez aller dans les paramètres "Sécurité et confidentialité" et sélectionner "Mot de passe requis * immédiatement * après le début de la mise en veille ou de l'économiseur d'écran"    
* Il est difficile de trouver une recette unique pour Arch Linux, espérons que ça ira. Si votre WM préférée n'est pas prise en charge, ouvrez simplement un ticket.
* Bien que nous ayons testé FreeBSD 11.2 et 12.1 qui ont tous les deux un moyen simple de verrouiller une session (xlock), jnativehook, notre lib de keylogger externe, ne prend pas en charge ce système d'exploitation, donc ne pourrons supporter cet OS.  
* Nous ne prenons en charge aucun système d'exploitation antérieur à la sortie du JDK 8.  
* Nous ne prenons pas en charge AIX car c'est un système d'exploitation mourant et nous doutons que quiconque l'utilisant encore ait jamais entendu parler de chocoblasts. 
