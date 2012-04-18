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

``Eclipse``

 $ play eclipsify


Run developement server
-----------------------

You can run the developement server with::

 $ play run
 
You can now access it on http://localhost:9000

Install SASS
------------

Install ruby::

 $ sudo apt-get install ruby

Install SASS and add it to your path variable::
  
 $ gem install sass
 $ export USR=$(whoami)
 $ echo "export PATH=$PATH:/home/$USR/.gem/ruby/1.9.1/bin" >> ~/.bashrc
 $ . ~/.bashrc

Watch file to automatically let SASS compile css::

 $ sass --watch style/sass:style/css

``SASS Information``: http://sass-lang.com/tutorial.html


Install CoffeeScript
--------------------

To run CoffeeScript you first have to install node.js::

 $ sudo apt-get install node.js

Then you can install the coffeescript compiler::
 
 $ sudo npm install -g coffee-script

Watch folder for changed (NOT newly generated) files and compile them::

 $ coffee -wbc -o script/js/ script/coffeescript/*.coffee

``A reference for CoffeeScript`` is available at http://coffeescript.org/
