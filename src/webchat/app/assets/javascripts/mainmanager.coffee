###
This handels all managers and the reloading of json data
###

class MainManager

    # constructor
    # domain: the domain with port, for instance www.example.com:9000
    # path: the relative url to the websocket
    # ssl: if true it will alter the websocket url to start with wss instead of ws
    constructor: (domain=document.location.host, path="/websocket", @ssl=false) ->
        if @ssl
            ws_url = "wss://" + domain + path
            console.log("using ssl")
        else
            ws_url = "ws://" + domain + path

        @keep_alive_interval = 30 # ping in seconds
        @groups = new GroupManager => 
            @init_managers()
        @users = new UserManager => 
            @init_managers()
        @channels = new ChannelManager => 
            @init_managers()
        @files = new FileManager => 
            @init_managers()
        @streams = new StreamManager => 
            @init_managers()
        @managers_initialized = 0
        @init_websocket(ws_url)


    # runs all migration methods for the managers once they were initialized
    init_managers: () ->
        @managers_initialized++
        if @managers_initialized == 5
            console.log("All managers initialized")
            @users.migrate_all()
            @channels.migrate_all()
            @streams.migrate_all()
            @groups.migrate_all()
            @files.migrate_all()

    # Intitializes the update interval to call the update method
    init_keep_alive: () ->
        console.log("setting keep alive interval to " + @keep_alive_interval + " seconds")
        @keep_alive_timer = setInterval =>
            @send_keep_alive()
        , @keep_alive_interval*1000


    # sets eventhandlers for websockets
    # ws_url: the url to which we connect
    init_websocket: (ws_url) ->
        console.log("initializing websockets on " + ws_url)
        Socket = window['MozWebSocket'] || window['WebSocket']
        try
            @connection = new Socket(ws_url)
            @connection.onopen = =>
                window.onunload = =>
                    @close_websocket()
                @init_keep_alive()
            @connection.onmessage = (event) =>
                @rcv_websocket(JSON.parse(event.data))
            @connection.onclose = =>
                console.log("closed websocket")
            @connection.onerror = =>
                console.log("websocket error occured")
        catch error
            console.log("Cant connect to " + ws_url)


    # sends a json object to the webserver
    # msg: the message whhich should be sent as json object
    send_websocket: (msg) ->
        msg = JSON.stringify(msg)
        console.log("sending from websocket " + msg)
        @connection.send(msg)


    # is called when the websocket receives data. the data is in json format
    # data: the received data json object
    rcv_websocket: (data) ->
        console.log("received from websocket " + JSON.stringify(data))
        if data.init
            switch data.type
                when "user" then @users.init(data.data)
                when "group" then @groups.init(data.data)
                when "stream" then @streams.init(data.data)
                when "channel" then @channels.init(data.data)
                when "file" then @files.init(data.data)
                when "status" then @status_msg(data.data)
        else
            switch data.type
                when "message" then @streams.update(data.data)
                when "user" then @users.update(data.data)
                when "group" then @groups.update(data.data)
                when "stream" then @streams.update(data.data)
                when "channel" then @channels.update(data.data)
                when "file" then @files.update(data.data)
                when "status" then @status_msg(data.data)


    # closes the websocket connection
    close_websocket: () ->
        console.log("closing websocket")
        @connection.close()


    # sends a keep alive signal to signal online to the server
    send_keep_alive: () ->
        message =
            type: "ping"
            data: {}
        @send_websocket(message)


    # method to handle error messages from the websocket
    # data: the data array with the messages
    # returns: true if data level is ok, otherwise false
    status_msg: (data) ->
        console.log("received status " + JSON.stringify(data))
        if data.level is "ok"
            true
        else
            alert(data.msg)


    # performs a json query and returns the data
    json_query: (url) ->
        console.log("querying " + url)
        $.getJSON url, (data) ->
            return data
            
    # sends a message to the server
    # msg: the message which we want to send
    # type: the type, text or java for instance
    send_msg: (msg, type) ->
        message =
            type: "message"
            data:
                message: msg
                type: type
                # FIXME: get active channel
                channel: [0]
        @send_websocket(message)
