###
This handels all managers and the reloading of json data
###

class MainManager

    # constructor
    constructor: (@ssl=false) ->
        @url_channels = "json/channels/"
        @url_users = "json/users/"
        @url_groups = "json/groups/"
        @url_files = "json/files/"
        @url_streams = "json/streams/"
        if @ssl
            @websocket_uri = "ws://localhost/websocket/"
            console.log("using ssl")
        else
            @websocket_uri = "wss://localhost/websocket/"
        @keep_alive_interval = 5

        @init_managers()
        @init_websocket()
        @init_keep_alive()


    # loads the intial data for the managers
    init_managers: () ->
        console.log("initializing managers")
        @users = new UserManager(@json_query(@url_users))
        @channels = new ChannelManager(@json_query(@url_channels))
        @groups = new GroupManager(@json_query(@url_groups))
        @files = new FileManager(@json_query(@url_files))
        @streams = new StreamManager(@json_query(@url_streams))


    # Intitializes the update interval to call the update method
    init_keep_alive: () ->
        console.log("setting keep alive interval to " + @keep_alive_interval + " seconds")
        @keep_alive_timer = setInterval =>
            @send_keep_alive()
        , @keep_alive_interval*1000


    # sets eventhandlers for websockets
    init_websocket: () ->
        console.log("initializing websockets")
        @connection = new WebSocket(@websocket_uri)
        # FIXME: only send to update after first ajax requests have been made to not lose information
        @connection.onmessage = (event) =>
            @rcv_websocket(JSON.parse(event.data))


    # sends a json object to the webserver
    send_websocket: (msg) ->
        msg = JSON.stringify(msg)
        console.log("sending from websocket " + msg)
        # TODO: check if auth information is available at the server
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


    # closes the websocket connection
    close_websocket: () ->
        console.log("closing websocket")
        @connection.close()


    # sends a keep alive signal to signal online to the server
    send_keep_alive: () ->
        message =
            "type": "ping"
        @send_websocket(message)


    # performs a json query and returns the data
    json_query: (url) ->
        console.log("querying " + url)
        $.getJSON url, (data) ->
            return data