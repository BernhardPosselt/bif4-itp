###
This handels all managers and the reloading of json data
###

class MainManager

    # constructor
    constructor: (@ssl=false) ->
        if @ssl
            @websocket_uri = "ws://localhost/websocket/"
            console.log("using ssl")
        else
            @websocket_uri = "wss://localhost/websocket/"
        @keep_alive_interval = 5 # ping in seconds
        @users = new UserManager()
        @channels = new ChannelManager()
        @groups = new GroupManager()
        @files = new FileManager()
        @streams = new StreamManager()
        @init_websocket()


    # loads the intial data for the managers
    init_managers: () ->
        console.log("initializing groups, users and channels")
        # init users
        init_users =
            type: "init"
            data:
                type: "users"
        @send_websocket(init_users)
        # init channels
        init_channels =
            type: "init"
            data:
                type: "channels"
        @send_websocket(init_channels)
        # init groups
        init_groups =
            type: "init"
            data:
                type: "groups"
        @send_websocket(init_groups)


    # Intitializes the update interval to call the update method
    init_keep_alive: () ->
        console.log("setting keep alive interval to " + @keep_alive_interval + " seconds")
        @keep_alive_timer = setInterval =>
            @send_keep_alive()
        , @keep_alive_interval*1000


    # sets eventhandlers for websockets
    init_websocket: () ->
        console.log("initializing websockets")
        # TODO: handle websocket error with try and catch
        @connection = new WebSocket(@websocket_uri)
        @connection.onopen = =>
            auth =
                # FIXME: look for session cookie and send value
                type: "auth"
                data:
                    sessionid: "abc"
            console.log("authenticating")
            if @send_websocket(auth)
                @init_managers()
                @init_keep_alive()
        # FIXME: only send to update after first ajax requests have been made to not lose information
        @connection.onmessage = (event) =>
            @rcv_websocket(JSON.parse(event.data))


    # sends a json object to the webserver
    send_websocket: (msg) ->
        msg = JSON.stringify(msg)
        console.log("sending from websocket " + msg)
        @connection.send(msg)


    # is called when the websocket receives data. the data is in json format
    rcv_websocket: (data) ->
        console.log("received from websocket " + JSON.stringify(data))
        switch data.type
            when "message" then @streams.update(data.data)
            when "user" then @users.update(data.data)
            when "group" then @groups.update(data.data)
            when "stream" then @streams.update(data.data)
            when "channel" then @channels.update(data.data)
            when "status" then @channels.status(data.data)


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