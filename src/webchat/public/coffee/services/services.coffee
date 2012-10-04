angular.module('WebChat').factory 'ChannelModel', ['_ChannelModel', (_ChannelModel) ->
    channel = new _ChannelModel()
    return channel
]

angular.module('WebChat').factory 'WebChatWebSocket', 
    ['_WebChatWebSocket', 'WEBSOCKET_DOMAIN', 'WEBSOCKET_PATH', 'WEBSOCKET_SSL',
     'ChannelModel',
    (_WebChatWebSocket, WEBSOCKET_DOMAIN, WEBSOCKET_PATH, WEBSOCKET_SSL, ChannelModel) =>

        models = [
            ChannelModel
        ]

        socket = new _WebChatWebSocket()
        socket.connect(WEBSOCKET_DOMAIN, WEBSOCKET_PATH, WEBSOCKET_SSL)
        socket.onReceive (message) ->
            for model in models
                if model.canHandle(message.type)
                    model.handle(message.data)
        return socket
    ]