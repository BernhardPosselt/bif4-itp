Developement Information
========================


Install SASS
------------

Install ruby::

 $ sudo apt-get install ruby

Install SASS and add it to your path variable::
  
 $ gem install sass
 $ export USR=$(whoami)
 $ echo 'export PATH=$PATH:/home/$USR/.gem/ruby/1.9.1/bin' >> ~/.bashrc
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

 $ coffee -wc -o script/js/ script/coffeescript/*.coffee

``A reference for CoffeeScript`` is available at http://coffeescript.org/
