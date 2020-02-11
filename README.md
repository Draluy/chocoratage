# Chocoratage

## Why ? / Pourquoi ?
##### English
In the French computer science sphere, we have two bright ideas colliding:
* There's two different words for the famous chocolate bread pastry: [_"pain au chocolat"_](https://en.wikipedia.org/wiki/Pain_au_chocolat) and [_"chocolatine"_](https://en.wikipedia.org/wiki/Pain_au_chocolat) depending which area you're from (plus _"couque au chocolat"_ in Beglium and _"croissant au chocolat"_ in Québec) and the battle is raging HARD!   
The split is analyzed [here](https://blog.adrienvh.fr/2012/10/16/cartographie-des-resultats-de-chocolatine-ou-pain-au-chocolat/) and [here](http://theconversation.com/pain-au-chocolat-vs-chocolatine-fight-85923).
* If you leave your computer unattended without locking it, the odds are that someone is gonna send a message or an email to the whole company saying you'll bring breakfast for everyone the next day. And obviously you'll bring chocolate breads amongst other viennoiseries...That office prank is called a **"chocoblast"**!
There's even [a bloody website](https://www.chocoblast.fr/) dedicated to it.

Isn't that great to tell everyone you'll bring _"chocolatines"_ the next day whereas you're writing from the computer of someone who was bred with _"pains au chocolat"_ ?

Yeah well to some extent: although we can understand leaving one's computer unattended may rise a security concern, and although we've been caught in a gentle way, like we're on a fresher initiation; we've also seen companies where they use it as a means of terror!  
Companies which warn you about the fact they actively promote chocoblasting on your very onboarding day! And they have a whole theory about it to the point you can't convince them it's kinda borderline to be chocoblasted while you're still seated on your chair and just turned away from your keyboard to ask a question to the colleague next to you and there's really no external people around...  
...and this is the tip of the iceberg, for, we've also seen cases where people blast you by writing preposterous stuff on your social networks! 
ENOUGH OF THOSE SICK IDIOTS !!!!

In french, _"to miss"_ is the verb _"rater"_, hence we counter attack the _"chocoblastage"_ anglicism with this project: _"chocoratage"_.

##### Français
Dans le petit monde de l'informatique française, nous faisons face à deux brillants concepts:
* Il y a deux mots différents pour désigner la viennoiserie contenant une barre de chocolat: [_"pain au chocolat"_](https://fr.wikipedia.org/wiki/Pain_au_chocolat) et [_"chocolatine"_](https://fr.wikipedia.org/wiki/Pain_au_chocolat) selon la région d'où vous venez (plus _"couque au chocolat"_ en Beglique et _"croissant au chocolat"_ au Québec) et la bataille fait rage SANS AUCUNE PITIÉ!  
La scission est analysée [ici](https://blog.adrienvh.fr/2012/10/16/cartographie-des-resultats-de-chocolatine-ou-pain-au-chocolat/) et [ici](http://theconversation.com/pain-au-chocolat-vs-chocolatine-fight-85923).  
* Si vous laissez votre ordinateur sans surveillance sans le verrouiller, il y a de fortes chances que quelqu'un envoie un message ou un e-mail à toute l'entreprise disant que vous apporterez le petit-déjeuner pour tout le monde le lendemain. Et évidemment, vous apporterez des chocolatines/pains au chocolat entre autres viennoiseries ... Cela s'appelle un ** "chocoblast" **!  
Il y a même [un putain de site](https://www.chocoblast.fr/) qui lui est dédié.

N'est-ce pas formidable d'écrire à tout le monde que vous apporterez des _"chocolatines"_ le lendemain alors que vous écrivez depuis l'ordinateur de quelqu'un qui a été élevé à coup de fournées de _"pains au chocolat"_?

Peut-être, dans une certaine mesure: même si nous pouvons comprendre que laisser son ordinateur sans surveillance peut poser un problème de sécurité, et bien que nous nous soyons nous-mêmes faits avoir de manière bienveillante, en mode bizutage, il est malheureusement des entreprises qui l'utilisent pour répandre la terreur!  
Des entreprises qui vous expliquent le jour-même de votre intégration qu'elles soutiennent activement le chocoblast! Et qui ont toute une théorie à ce sujet au point que vous ne pouvez pas les convaincre que c'est un peu limite d'être chocoblasté alors que vous êtes toujours assis sur votre chaise et que vous vous êtes juste détourné de votre clavier pour poser une question au collègue d'à côté, et qu'il n'y a personne d'extérieur aux alentours...  
... et encore c'est gentillet quand on a aussi vu des cas où les gens vous blastent en écrivant des choses incongrues sur vos réseaux sociaux!  
ON EN A ASSEZ DE CES CRÉTINS !!!!

Donc on contre-attaque le _"chocoblastage"_ avec ce projet: _"chocoratage"_.

## How it works / Comment ça marche

##### English
Chocoratage is a Java 8 program. It comes with a list of french breakfast-related keywords & phrases. It detects your OS upon startup and listens to your keystrokes using a lightweight keylogger. Whenever anyone types a set of keys that looks like one of the forbidden phrases, it automatically locks your session!  
The program uses a Levenshtein algorithm for forbidden phrases matching.  
That will not make your computer 100% chocoblast-proof cause your dear coleagues can become real creative when they're hungry, but it will save your ass at least a couple of times.

##### Français
Chocoratage est écrit en Java 8. It embarque une liste de mots-clés et bouts de phrases sur le thème du petit déjeuner. Il détecte votre OS au démarrage et écoute votre clavier à l'aide d'un keylogger très léger. Lorsque quelqu'un tape une suite de caractères approchante d'un des termes interdits, il vérouille automatiquement votre session!     
Le programme utilise un algo de Levenshtein pour la reocnnaissance des termes interdits.  
Ca ne rendra pas votre ordinateur totalement imperméable au chocoblastage, vos gentils collègues pouvant devenir vraiment créatifs quand ils ont faim, néanmoins cela vous sauvera bien la mise une ou deux fois.

## Installation
##### English
Just download the uberjar, and run it using `java -jar chocoratage.jar`  
You may use the .sh/.bat provided in the /scripts folder    
That's all.

Obviously we can't run this program in a Docker. We could pass ENV vars to let the program infer which host the VM is running on, but we would be unable to grab the keyboard strokes or lock the host's session.  

##### Français
Téléchargez le uberjar, et lancez-le simplement avec `java -jar chocoratage.jar`  
Vous pouvez éventuellement utiliser le .bat ou .sh que vous trouverez dans /scripts  
C'est tout.

Evidemment, il n'est pas possible de lancer ce programme dans un Docker. Même s'il est possible de lui passer des vars d'ENV pour lui permettre de détecter l'OS du host, il serait très difficile de capter le clavier et encore moins de vérouiller la session du host. 

## Command line switches / options de ligne de commande
- **h/help** : the help / l'aide :)
- **d/debug** : trace events on the console / trace les évènements du programme sur la console
- **f/forbidden**=&lt;path&gt; : supply a list of forbidden phrases, one per line, each may have several words, to replace the default one / fournir une liste de termes interdits, un par ligne, pouvant comporter plusieurs mots chacun, pour remplacer celui par défaut. 
- **l/lock** : lock session at startup / vérouille la session au démarrage
- **o/os**=&lt;os&gt; : force the os, available values are / force l'os, valeurs aurorisées : LINUX, WINDOWS, OSX, FREEBSD, SOLARIS, AIX, OTHER_UNIX, UNKNOWN although only the first 5 ones are actually wired to trigger session locking / bien que seuls les 5 premiers soient câblés pour permettre le vérouillaage d'une session.  
- **strict**=&lt;true/false&gt; : pattern matching must match exactly, default false / fait que la reconnaissance est stricte, faux par défaut. 
- **s/simu** : log instead of locking / loggue au lieu de vérouiller la session.


## Compatibility / Compatibilité
##### English
We tested and enabled an efficient locking on VMs or real hardware running the following OSes and distributions:

##### Français
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

##### English
* Obviously plenty of other distributions will also work (eg other Fedora's, Debian's, Windows 8, OSX Mavericks, El Capitan, etc...).
* That said, OSX Catalina terrible security policy makes it hard to merely run java, there's so uch we can do about it...
* By the way, for the lock screen to actually protect you on OSX, you must go to the "Security & Privacy" settings and select "Require password *immediately* after sleep or screensaver begins   
* It's hard to find a one-size-fits-all recipe to Arch Linux but let's hope for the best. If your favorite WM isn't supported, just open a ticket. Or get a life :)
* Although we tested FreeBSD 11.2 and 12.1 which both have a straightforward way to lock a session (xlock), jnativehook, our keylogger lib doesn't support that OS, hence we won't either.  
* We don't support any OS anterior to the release of the JDK 8.  
* We don't support AIX as it's a dying OS and we doubt anyone still using it has ever heard of chocoblasts, duh!  

##### Français
* De toute évidence, de nombreuses autres distributions sont également compatibles (ex:, d'autres Fedora, Debian, Windows 8, OSX Mavericks, El Capitan, etc ...).
* Cela dit, OSX Catalina a une politique de sécurité horrible qui rendra même l'exécution de java difficile. Nous n'y pouvons pas grand-chose...
* D'ailleurs, pour que l'écran de verrouillage vous protège réellement sur OSX, vous devez aller dans les paramètres "Sécurité et confidentialité" et sélectionner "Mot de passe requis * immédiatement * après le début de la mise en veille ou de l'économiseur d'écran"    
* Il est difficile de trouver une recette unique pour Arch Linux, espérons que ça ira. Si votre WM préférée n'est pas prise en charge, ouvrez simplement un ticket. Ou ayez une vie :)
* Bien que nous ayons testé FreeBSD 11.2 et 12.1 qui ont tous les deux un moyen simple de verrouiller une session (xlock), jnativehook, notre lib de keylogger externe, ne prend pas en charge ce système d'exploitation, donc ne pourrons supporter cet OS.  
* Nous ne prenons en charge aucun système d'exploitation antérieur à la sortie du JDK 8.  
* Nous ne prenons pas en charge AIX car c'est un système d'exploitation mourant et nous doutons que quiconque l'utilisant encore ait jamais entendu parler de chocoblastes, aha!  

## Disclaimer / Exonération de responsabilité
##### English
We can't be held responsible if your dear colleagues manage to circumvent our program.
We can' be held responsible for any misuse/loss of information/lagging.
Likewise, since this program uses a keylogger we can't be held responsible for any wrongdoing done using our code.

##### Français
Nous ne pouvons être tenus responsables si vos chers collègues parviennent à contourner notre programme.
Nous pouvons être tenus responsables de toute utilisation abusive / perte d'informations / ralentissements.
De même, puisque ce programme utilise un keylogger, nous ne pouvons être tenus responsables de tout acte répréhensible du à une réutilisation de notre code.