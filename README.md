# Chocoratage

## Why ? / Pourquoi ?
##### English
If you leave your computer unattended without locking it, the odds are that someone is gonna send a message or an email to the whole company saying you'll bring breakfast for everyone the next day. And obviously you'll bring chocolate breads amongst other viennoiseries...That office prank is called a **"chocoblast"**!
There's even [a website](https://www.chocoblast.fr/) dedicated to it.

The reason given for this is to improve security by giving developers the habit of locking their workstations.
Though there are issues to this reasoning:
* It belittles developers into [pavlovian dogs](https://en.wikipedia.org/wiki/Classical_conditioning), by assuming that talking to their intellect will be useless. 
* It assumes that the work environment is hostile. However, if such is the case, then the environment itself is an issue, and needs to be changed.
* It poses denunciation and reward as a framework. It doesn't promote team spirit. How can one hope colleages bullying each other all year will be united once a production issue will strike?

The appropriate behavior is this: attend to the colleagues workstation when they briefly leave their desk, the same way they will do it for us; lock the workstation, and warn the culprit in a gentle way. That's a more positive manner to address the issue. 

In french, _"to miss"_ is the verb _"rater"_, hence we counter-attack the _"chocoblastage"_ anglicism with this project: _"chocoratage"_.

##### Français
Si vous laissez votre ordinateur sans surveillance et sans le verrouiller, il y a de fortes chances que quelqu'un envoie un message ou un e-mail à toute l'entreprise disant que vous apporterez le petit-déjeuner pour tout le monde le lendemain. Cela s'appelle un ** "chocoblast" **!  
Il y a même [un site](https://www.chocoblast.fr/) qui lui est dédié.

La raison invoquée pour cette pratique est d'améliorer la securité en donnant l'habitude aux développeurs de  verrouiler leurs postes.
Or, ce raisonnement est problématique:

* Il rabaisse les développeurs a des [chiens de pavlov](https://fr.wikipedia.org/wiki/Conditionnement_classique), en partant du principe que parler à leur intellect s'avèrera infructueux.
* Il part du principe que l'environnement de travail est hostile. Or, si c'est le cas, c'est l'environnement qui est problématique, et c'est lui qui doit être changé.
* Il donne la délation et la récompense comme modèle de fonctionnement. Cela ne favorise pas la cohésion d'équipe. Comment espérer que des collègues qui se tirent dans les pattes toute l'année fassent preuve de cohésion lorsqu'un problème de production se présentera?

Un comportement adapté est le suivant: surveiller le PC de nos collègues lorsqu'ils s'absentent momentanément, comme ils le feront pour nous; et verrouiller le poste qui doit l'être, en avertissant gentiment le fautif. C'est une manière plus positive de gérer le même problème.

Donc nous contre-attaquons le _"chocoblastage"_ avec ce projet: _"chocoratage"_.


## How it works / Comment ça marche

##### English
Chocoratage is a Java 8 program. It comes with a list of french breakfast-related keywords & phrases. It detects your OS upon startup and listens to your keystrokes using a lightweight keylogger. Whenever anyone types a set of keys that looks like one of the forbidden phrases, it automatically locks your session.
By default the program uses a Levenshtein algorithm for forbidden phrases matching. Another, more efficient normalization-based algorithm is available, for tough cases.   
That will not make your computer 100% chocoblast-proof as your coleagues can become real creative when they're hungry, but it will save your skin a couple of times.

##### Français
Chocoratage est écrit en Java 8. It embarque une liste de mots-clés et bouts de phrases sur le thème du petit déjeuner. Il détecte votre OS au démarrage et écoute votre clavier à l'aide d'un keylogger très léger. Lorsque quelqu'un tape une suite de caractères approchante d'un des termes interdits, il vérouille automatiquement votre session.     
Par défaut, le programme utilise un algo de Levenshtein pour la reconnaissance des termes interdits. Il dispose aussi d'un autre algorithme basé sur la nomalisation pour les cas difficiles.   
Ca ne rendra pas votre ordinateur totalement imperméable au chocoblastage, vos collègues pouvant devenir créatifs quand ils ont faim, néanmoins cela vous sauvera bien la mise une ou deux fois.

## Installation
##### English
Just download the uberjar, and run it using `java -jar chocoratage.jar`  
You may use the .sh/.bat provided in the /scripts folder    
That's all.

Obviously we can't run this program in a Docker. We could pass ENV vars to let the program infer which host the VM is running on, but we would be unable to grab the keyboard strokes or lock the host's session.  

##### Français
Téléchargez le uberjar, et lancez-le simplement avec `java -jar chocoratage.jar`  
Vous pouvez éventuellement utiliser le .bat ou .sh que vous trouverez dans /scripts.
C'est tout.

Evidemment, il n'est pas possible de lancer ce programme dans un Docker. Même s'il est possible de lui passer des vars d'ENV pour lui permettre de détecter l'OS du host, il serait très difficile de capter le clavier et encore moins de vérouiller la session du host. 

## Command line switches / options de ligne de commande
- **h/help** : the help / l'aide :)
- **d/debug** : trace events on the console / trace les évènements du programme sur la console
- **f/forbidden**=&lt;path&gt; : supply a list of forbidden phrases, one per line, each may have several words, to replace the default one / fournir une liste de termes interdits, un par ligne, pouvant comporter plusieurs mots chacun, pour remplacer celui par défaut. 
- **l/lock** : lock session at startup / vérouille la session au démarrage
- **o/os**=&lt;os&gt; : force the os, available values are / force l'os, valeurs aurorisées : LINUX, WINDOWS, OSX, FREEBSD, SOLARIS, AIX, OTHER_UNIX, UNKNOWN although only the first 5 ones are actually wired to trigger session locking / bien que seuls les 5 premiers soient câblés pour permettre le vérouillaage d'une session.  
- **m/mode**=&lt;equal/levenshtein/normalizer&gt; : matching algorithm, default is Levenshtein / algorithme de matching, Levenshtein par défaut
- **s/simu** : log instead of locking / loggue au lieu de vérouiller la session.


## Compatibility / Compatibilité
##### English
See the OS.md file for the list of OSes we tested on VMs or real hardware in 2020 and for which we enabled an efficient locking mechanism.
The program supports most Linux distributions, Windows, OSX, and even FeeBSD and Solaris versions. 

##### Français
Veuillez vous référer au fichier OS.md pour la listes des OS que nous avons testé sur des VMs ou du matériel en 2020, et pour lesquels nous avons câblé un vérouillage efficace. 
Le programme prend en charge quasi-totalité des distributions Linux, ainsi que Windows, OSX et même des versions de FreeBSD et Solaris 


##### English
* Obviously plenty of other distributions will also work (eg other Fedora's, Debian's, Windows 8, OSX Mavericks, El Capitan, etc...).
* That said, OSX Catalina terrible security policy makes it hard to merely run java, there's so uch we can do about it...
* By the way, for the lock screen to actually protect you on OSX, you must go to the "Security & Privacy" settings and select "Require password *immediately* after sleep or screensaver begins   
* It's hard to find a one-size-fits-all recipe to Arch Linux but let's hope for the best. If your favorite WM isn't supported, just open a ticket. Or get a life :)
* Although we tested FreeBSD 11.2 and 12.1 which both have a straightforward way to lock a session (xlock), jnativehook, our keylogger lib doesn't support that OS, hence we won't either.  
* We don't support any OS anterior to the release of the JDK 8.  
* We don't support AIX as it's a dying OS and we doubt anyone still using it has ever heard of chocoblasts.  

##### Français
* De toute évidence, de nombreuses autres distributions sont également compatibles (ex:, d'autres Fedora, Debian, Windows 8, OSX Mavericks, El Capitan, etc ...).
* Cela dit, OSX Catalina a une politique de sécurité horrible qui rendra même l'exécution de java difficile. Nous n'y pouvons pas grand-chose...
* D'ailleurs, pour que l'écran de verrouillage vous protège réellement sur OSX, vous devez aller dans les paramètres "Sécurité et confidentialité" et sélectionner "Mot de passe requis * immédiatement * après le début de la mise en veille ou de l'économiseur d'écran"    
* Il est difficile de trouver une recette unique pour Arch Linux, espérons que ça ira. Si votre WM préférée n'est pas prise en charge, ouvrez simplement un ticket.
* Bien que nous ayons testé FreeBSD 11.2 et 12.1 qui ont tous les deux un moyen simple de verrouiller une session (xlock), jnativehook, notre lib de keylogger externe, ne prend pas en charge ce système d'exploitation, donc ne pourrons supporter cet OS.  
* Nous ne prenons en charge aucun système d'exploitation antérieur à la sortie du JDK 8.  
* Nous ne prenons pas en charge AIX car c'est un système d'exploitation mourant et nous doutons que quiconque l'utilisant encore ait jamais entendu parler de chocoblasts. 
