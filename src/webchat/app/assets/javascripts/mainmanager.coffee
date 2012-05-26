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
        @channels = new ChannelManager => 
            @init_managers()
        , this
        @managers_initialized = 0
        @init_websocket(ws_url)


    # runs all migration methods for the managers once they were initialized
    init_managers: () ->
        @managers_initialized++
        if @managers_initialized == 4
            console.log("All managers initialized")
            @channels.init_ui()


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
                window.onbeforeunload = =>
                    @close_websocket()
                @init_keep_alive()
                init_msg = 
                    type: "init"
                    data: {}
                @send_websocket(init_msg)
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
                when "user" then @channels.init_user(data.data)
                when "activeuser" then @channels.init_active_user(data.data)
                when "group" then @channels.init_group(data.data)
                when "channel" then @channels.init(data.data)
                when "file" then @channels.init_file(data.data)
                when "message" then @channels.init_stream(data.data)                
                when "status" then @status_msg(data.data)
        else
            switch data.type
                when "user" then @channels.input_user(data.data, data.actions)
                when "group" then @channels.input_group(data.data, data.actions)
                when "channel" then @channels.input(data.data, data.actions)
                when "file" then @channels.input_file(data.data, data.actions)
                when "message" then @channels.input_stream(data.data, data.actions)
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
          
          
    # changes the topic for the channel
    # topic: the new topic text
    # channel_id: the id of the channel where the topic should be changed
    change_topic: (topic, channel_id=@channels.get_active_channel()) ->
        message =
            type: "channeltopic"
            data:
                topic: topic
                channel: channel_id
        @send_websocket(message)


    # changes the name for the channel
    # name: the new channel name
    # channel_id: the id of the channel where the topic should be changed
    change_channel_name: (name, channel_id=@channels.get_active_channel()) ->
        message =
            type: "channelname"
            data:
                name: name
                channel: channel_id
        @send_websocket(message)
        
    # deletes a channel
    # channel_id: the id of the channel where the topic should be changed
    delete_channel: (channel_id=@channels.get_active_channel()) ->
        message =
            type: "channeldelete"
            data:
                name: name
                channel: channel_id
        @send_websocket(message)

        
    # sends a message to the server
    # msg: the message which we want to send
    # type: the type, text or java for instance
    send_msg: (msg, type) ->
        channel_id = @channels.get_active_channel()
        if channel_id != undefined
            channel_id = parseInt(channel_id)
            # check for cmd prefixes
            # topic changes the channel topic
            message = {}
            if msg.indexOf("/topic ") == 0 and type == "text"
                @change_topic(msg.substr(7))
            
            # no prefix, then this is a normal message
            else
                message =
                    type: "message"
                    data:
                        message: msg
                        type: type
                        channel: [channel_id]
                @send_websocket(message)
      
      
    # returns the completed name of a person in the channel if possible
    complete_name: (val) ->
        return @channels.complete_name(val)
    
    # moves all users and groups from selected to unselected
    reset_invite_selection: ->
        @channels.reset_invite_selection()
    
    # invites all selected groups and users
    invite_selection: ->
        selected_users_and_groups = @channels.get_invite_selection()
        active_channel = parseInt(@channels.get_active_channel())
        users = selected_users_and_groups.users
        groups = selected_users_and_groups.groups
        msg = 
            type: "invite"
            data:
                channel: active_channel
                users: users
                groups: groups
        # only send if there min 1 user or 1 group
        if users.length > 0 or groups.length > 0
            @send_websocket(msg)
            
    # creates a new channel
    create_channel: (name, topic, is_public) ->
        msg = 
            type: "newchannel"
            data: 
                name: name
                topic: topic
                is_public: is_public
        if name != ""
            @send_websocket(msg)
