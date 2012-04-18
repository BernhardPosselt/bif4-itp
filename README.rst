Developement Information
========================

Install Play
------------
Download the latest Play zip (http://download.playframework.org/releases/play-2.0.zip) and extract it to your home directory. Then proceed with the install information for your operating system. 

Install Linux::

 $ sudo mv play-2.0/ /opt
 $ sudo chmod a+x /opt/play-2.0/play
 $ sudo ln -s /opt/play-2.0/play /usr/bin/play

Generate IDE Project files
--------------------------

To generate the project files for your IDE, just cd into the webchat directory and
execute the specific commands.

``Intellij IDEA``::

 $ play idea

Then go: File -> New Module -> Import Existing Module and select the generated the webchat.iml

``Eclipse``::

 $ play eclipsify


Run developement server
-----------------------

You can run the developement server with::

 $ play run
 
You can now access it on http://localhost:9000

LESS
----

LESS Files reside in the app/assets/stylesheets directory and are automatically
compiled into public/stylesheet

``LESS Information``: http://lesscss.org/


CoffeeScript
------------
CoffeeScript Files reside in the app/assets/javascript directory and are automatically
compiled into public/javascript


``A reference for CoffeeScript`` is available at http://coffeescript.org/
