Developement Information
========================

Install Play
------------
Download the latest Play zip (http://download.playframework.org/releases/play-2.0.zip) and extract it to your home directory. Then proceed with the install information for your operating system. 

Install Linux::

 $ sudo mv play-2.0/ /opt
 $ sudo chmod a+x /opt/play-2.0/play
 $ sudo ln -s /opt/play-2.0/play /usr/local/bin/play

Generate IDE Project files
--------------------------

To generate the project files for your IDE, just cd into the webchat directory and
execute the specific commands. The play commands have to be used in this directory

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

Generate development data
-------------------------
As the project is currently pre-alpha, you have to generate standard user accounts by yourself by visiting this link
ONCE every time you start the developement server::

  http://localhost:9000/Testdata

It will ask you to generate the database in your memory. After this you can log in under 

  http://localhost:9000/

The Testusers are::

  USER:PASSWORD
  MasterLindi:test
  Glembo:test

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

Deployment
----------

You can start the Production Server with the commands:


    $ play clean compile stage
    $ ./target/start -DapplyEvolutions.default=true -Dhttp.port=80

With this commands you start a Server with Port 80 and the Model Scripts are executed automatically.

Database
----------

In our project we use 2 diffenrent DB-Systems.

For develepment we use the In-Memory-Database H2 with following configuration:
    db.default.driver=org.h2.Driver
    db.default.url="jdbc:h2:mem:play"
    # db.default.user=sa
    # db.default.password=

As you can see for this DB you do not need a User or a Password. 
If you like a GUI for the H2 Database you need the command:
    $ play h2-browser

In production we use the Mysql-Database with following configuration:

    db.default.driver=com.mysql.jdbc.Driver
    db.default.url="jdbc:mysql://localhost/itp_4?characterEncoding=UTF-8"
    db.default.user=<user>
    db.default.password=<"password">
