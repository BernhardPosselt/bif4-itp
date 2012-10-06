angular.module('WebChat').factory 'ChannelModel', ['_ChannelModel', (_ChannelModel) ->
    channelmodel = new _ChannelModel()
    return channelmodel
]

angular.module('WebChat').factory 'GroupModel', ['_GroupModel', (_GroupModel) ->
    groupmodel = new _GroupModel()
    return groupmodel
]

angular.module('WebChat').factory 'UserModel', ['_UserModel', (_UserModel) ->
    usermodel = new _UserModel()
    return usermodel
]

angular.module('WebChat').factory 'FileModel', ['_FileModel', (_FileModel) ->
    filemodel = new _FileModel()
    return filemodel
]

angular.module('WebChat').factory 'MessageModel', ['_MessageModel', (_MessageModel) ->
    messagemodel = new _MessageModel()
    return messagemodel
]

angular.module('WebChat').factory 'MimeTypes', ['_MimeTypes', (_MimeTypes) ->
    mimetypes = new _MimeTypes()
    return mimetypes
]


angular.module('WebChat').factory 'WebChatWebSocket', 
    ['_WebChatWebSocket', 'WEBSOCKET_DOMAIN', 'WEBSOCKET_PATH', 'WEBSOCKET_SSL',
     'ChannelModel', 'GroupModel', 'UserModel', 'FileModel'
    (_WebChatWebSocket, WEBSOCKET_DOMAIN, WEBSOCKET_PATH, WEBSOCKET_SSL, 
     ChannelModel, GroupModel, UserModel, FileModel) =>

        models = [
            ChannelModel
            GroupModel
            UserModel
            FileModel
        ]

        socket = new _WebChatWebSocket()
        socket.connect(WEBSOCKET_DOMAIN, WEBSOCKET_PATH, WEBSOCKET_SSL)
        socket.onReceive (message) ->
            for model in models
                if model.canHandle(message.type)
                    model.handle(message.data)
        return socket
    ]