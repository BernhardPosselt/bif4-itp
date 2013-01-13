angular.module('WebChat').factory 'WebSocketPublisher', ['_WebSocketPublisher', (_WebSocketPublisher) ->
    publisher = new _WebSocketPublisher()
    return publisher
]

angular.module('WebChat').factory 'ChannelModel', ['_ChannelModel', 'WebSocketPublisher', 
(_ChannelModel, WebSocketPublisher) ->
    model = new _ChannelModel()
    WebSocketPublisher.subscribe(model.getModelType(), model)
    return model
]

angular.module('WebChat').factory 'GroupModel', ['_GroupModel', 'WebSocketPublisher', 
(_GroupModel, WebSocketPublisher) ->
    model = new _GroupModel()
    WebSocketPublisher.subscribe(model.getModelType(), model)
    return model
]

angular.module('WebChat').factory 'UserModel', ['_UserModel', 'WebSocketPublisher', 
(_UserModel, WebSocketPublisher) ->
    model = new _UserModel()
    WebSocketPublisher.subscribe(model.getModelType(), model)
    return model
]

angular.module('WebChat').factory 'FileModel', ['_FileModel', 'WebSocketPublisher', 
(_FileModel, WebSocketPublisher) ->
    model = new _FileModel()
    WebSocketPublisher.subscribe(model.getModelType(), model)
    return model
]

angular.module('WebChat').factory 'MessageModel', ['_MessageModel', 'WebSocketPublisher', 
(_MessageModel, WebSocketPublisher) ->
    model = new _MessageModel()
    WebSocketPublisher.subscribe(model.getModelType(), model)
    return model
]

angular.module('WebChat').factory 'ActiveUser', ['_ActiveUser', 'WebSocketPublisher', 
(_ActiveUser, WebSocketPublisher) ->
    model = new _ActiveUser()
    WebSocketPublisher.subscribe(model.getModelType(), model)
    return model
]

angular.module('WebChat').factory 'ActiveChannel', ['_ActiveChannel', (_ActiveChannel) ->
    model = new _ActiveChannel()
    return model
]

angular.module('WebChat').factory 'MimeTypes', ['_MimeTypes', (_MimeTypes) ->
    mimetypes = new _MimeTypes()
    return mimetypes
]

angular.module('WebChat').factory 'Smileys', ['_Smileys', (_Smileys) ->
    smileys = new _Smileys()
    return smileys
]


angular.module('WebChat').factory 'WebChatWebSocket', 
['_WebChatWebSocket', 'WEBSOCKET_DOMAIN', 'WEBSOCKET_PATH', 'WEBSOCKET_SSL',
'WebSocketPublisher', 'FileModel', 'MessageModel', 'ActiveUser', 'UserModel',
'ChannelModel', 'GroupModel',
(_WebChatWebSocket, WEBSOCKET_DOMAIN, WEBSOCKET_PATH, WEBSOCKET_SSL,
WebSocketPublisher) ->

    socket = new _WebChatWebSocket()
    socket.connect(WEBSOCKET_DOMAIN, WEBSOCKET_PATH, WEBSOCKET_SSL)
    socket.onReceive (message) ->
        WebSocketPublisher.publish(message.type, message)

    return socket
]