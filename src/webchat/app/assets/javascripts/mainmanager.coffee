###
This handels all managers and the reloading of json data
###

class MainManager

    # constructor
    constructor: (url="localhost/websocket/", @ssl=false) ->
        if @ssl
            @websocket_url = "wss://" + url
            console.log("using ssl")
        else
            @websocket_url = "ws://" + url

        @keep_alive_interval = 5 # ping in seconds
        @users = new UserManager()
        @channels = new ChannelManager()
        @groups = new GroupManager()
        @files = new FileManager()
        @streams = new StreamManager()
        @init_websocket()



    # Intitializes the update interval to call the update method
    init_keep_alive: () ->
        console.log("setting keep alive interval to " + @keep_alive_interval + " seconds")
        @keep_alive_timer = setInterval =>
            @send_keep_alive()
        , @keep_alive_interval*1000


    # sets eventhandlers for websockets
    init_websocket: () ->
        console.log("initializing websockets on " + @websocket_uri)
        Socket = window['MozWebSocket'] || window['WebSocket']
        try
            @connection = new Socket(@websocket_url)
            @connection.onopen = =>
                auth =
                    # FIXME: look for session cookie and send value
                    type: "auth"
                    data:
                        sessionid: "abc"
                console.log("authenticating")
                @init_keep_alive()
            @connection.onmessage = (event) =>
                @rcv_websocket(JSON.parse(event.data))
            @connection.onclose = =>
                console.log("closed websocket")
            @connection.onerror = =>
                console.log("websocket error occured")
        catch error
            console.log("Cant connect to " + @websocket_url)


    # sends a json object to the webserver
    send_websocket: (msg) ->
        msg = JSON.stringify(msg)
        console.log("sending from websocket " + msg)
        @connection.send(msg)


    # is called when the websocket receives data. the data is in json format
    rcv_websocket: (data) ->
        console.log("received from websocket " + JSON.stringify(data))
        if data.init
            switch data.type
                when "user" then @users.init(data.data)
                when "group" then @groups.init(data.data)
                when "stream" then @streams.init(data.data)
                when "channel" then @channels.init(data.data)
                when "status" then @status_msg(data.data)
        else
            switch data.type
                when "message" then @streams.update(data.data)
                when "user" then @users.update(data.data)
                when "group" then @groups.update(data.data)
                when "stream" then @streams.update(data.data)
                when "channel" then @channels.update(data.data)
                when "status" then @status_msg(data.data)


    # closes the websocket connection
    close_websocket: () ->
        console.log("closing websocket")
        @connection.close()


    # sends a keep alive signal to signal online to the server
    send_keep_alive: () ->
        message =
            "type": "ping"
            "data": {}
        @send_websocket(message)


    # method to handle error messages from the websocket
    # data: the data array with the messages
    # returns: true if data level is ok, otherwise false
    status_msg: (data) ->
        console.log("received status " + JSON.stringify(data))
        # TODO: alert message for signaling error
        if data.level is "ok"
            true
        else
            false


    # performs a json query and returns the data
    json_query: (url) ->
        console.log("querying " + url)
        $.getJSON url, (data) ->
            return data