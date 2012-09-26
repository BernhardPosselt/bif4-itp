class WebchatWebsocket

    constructor: ->
        @_callbacks = 
            onOpen: -> 
            onReceive: ->
            onError: ->
                console.error("websocket error occured")
            onClose: ->
                console.info("closed websocket")


    # domain: the domain with port, for instance www.example.com:9000
    # path: the relative url to the websocket
    # ssl: if true it will alter the websocket url to start with wss instead of ws
    connect: (domain=document.location.host, path="/websocket", @ssl=false) ->
        Socket = window['MozWebSocket'] || window['WebSocket']

        if @ssl
            protocol = "wss://"
        else
            protocol = "ws://"

        url = "#{protocol}#{domain}#{path}"

        try
            @_connection = new Socket(url)            
            @_connection.onopen = =>
                @_callbacks.onOpen()
                window.onbeforeunload = =>
                    @close()
            
            @_connection.onmessage = (event) =>
                msg = event.data
                json = JSON.parse(msg)
                @_callbacks.onReceive(json)
                console.info("Received: #{msg}")
            
            @_connection.onclose = =>
                @_callbacks.onClose()

            @_connection.onerror = =>
                @_callbacks.onError()

        catch error
            console.error("Cant connect to #{url}")


    # register callbacks
    onOpen: (callback) ->
        @_callbacks.onOpen = callback


    onReceive: (callback) ->
        @_callbacks.onReceive = callback


    onClose: (callback) ->
        @_callbacks.onClose = callback


    onError: (callback) ->
        @_callbacks.onError = callback


    send: (msg) ->
        @_connection.send(msg)
        console.info("Sending #{msg}")


    # sends a json object to the webserver
    sendJSON: (json_object) ->
        msg = JSON.stringify(json_object)
        @send(msg)

