WebChat = window.WebChat

$(document).ready ->
    main = new Main()
    main.initWebsocket()
    main.registerController('[ng-controller=ChannelController]')

    #main.distributor.notifyController({ type: "channel", action: "delete", data: {id: 0, name: "test"}})


class Main

    constructor: () ->
        @distributor = new WebChat.Distributor()
        @distributor.setCache(new WebChat.Cache())
    
    registerController: (selector) ->
        @distributor.registerController($(selector)[0])

    initWebsocket: (e) ->
        @websocket = new WebChat.WebSocket()
        @websocket.connect()
        @websocket.onReceive(@distributor.notifyController)
