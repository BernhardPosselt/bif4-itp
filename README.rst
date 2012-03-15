Developement Information
========================


Install SASS
------------

Install ruby::

 $ sudo apt-get install ruby

Install SASS and add it to your path variable::
  
 $ gem install sass
 $ export USR=$(whoami)
 $ echo 'export PATH=$PATH:/home/$USR/.gem/ruby/1.9.1/bin"

Watch file to automatically let SASS compile css::

 $ sass --watch style/sass:style/css

SASS Information: http://sass-lang.com/tutorial.html