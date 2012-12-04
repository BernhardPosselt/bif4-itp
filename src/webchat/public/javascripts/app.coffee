app = angular.module('WebChat', []).
    config ($provide) ->
        # enter your config values in here
        $provide.value('WEBSOCKET_DOMAIN', document.location.host)
        $provide.value('WEBSOCKET_PATH', '/websocket')
        $provide.value('WEBSOCKET_SSL', false)
        $provide.value('PING_TIMEOUT', 5000)
        return

app.run ($rootScope) ->

$(document).ready ->
    SyntaxHighlighter.defaults.collapse = true


app = angular.module('WebChat', []).
    config ($provide) ->
        # enter your config values in here
        $provide.value('WEBSOCKET_DOMAIN', document.location.host)
        $provide.value('WEBSOCKET_PATH', '/websocket')
        $provide.value('WEBSOCKET_SSL', false)
        $provide.value('PING_TIMEOUT', 5000)
        return

app.run ($rootScope) ->

$(document).ready ->
    SyntaxHighlighter.defaults.collapse = true


angular.module('WebChat').factory 'ActiveChannel', ['$rootScope', ($rootScope) ->
    ActiveChannel = {}
    ActiveChannel.activeChannelId = null

    ActiveChannel.setActiveChannelId = (id) ->
        @activeChannelId = id
        $rootScope.$broadcast 'changed_channel'

    ActiveChannel.getActiveChannelId = ->
        return @activeChannelId

    return ActiveChannel
]

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

angular.module('WebChat').factory 'Smileys', ['_Smileys', (_Smileys) ->
    smileys = new _Smileys()
    return smileys
]


angular.module('WebChat').factory 'WebChatWebSocket', 
    ['_WebChatWebSocket', 'WEBSOCKET_DOMAIN', 'WEBSOCKET_PATH', 'WEBSOCKET_SSL',
     'FileModel', 'UserModel', 'GroupModel', 'ActiveUser', 'ChannelModel', 'MessageModel',
    (_WebChatWebSocket, WEBSOCKET_DOMAIN, WEBSOCKET_PATH, WEBSOCKET_SSL,
        FileModel, UserModel, GroupModel, ActiveUser, ChannelModel, MessageModel) =>

        handlers = [
            FileModel
            ChannelModel
            GroupModel
            UserModel
            ActiveUser
            MessageModel
        ]

        socket = new _WebChatWebSocket()
        socket.connect(WEBSOCKET_DOMAIN, WEBSOCKET_PATH, WEBSOCKET_SSL)
        socket.onReceive (message) ->
            for handler in handlers
                if handler.canHandle(message)
                    handler.handle(message)

        return socket
    ]

angular.module('WebChat').factory '_Smileys', [ ->

    class Smileys

        constructor: () ->
            @path = "/assets/images/smileys/"
            @smileys =
                ":)": "080.gif"
                "-_-": "107.gif"
                ":/": "003.gif"
                ":(": "030.gif"
                ":D": "074.gif"
                "xD": "049.gif"
                "XD": "049.gif"
                ";D": "073.gif"
                "&gt;.&lt;": "009.gif"
                "-.-": "064.gif"
                "-.-*": "064.gif"
                ";)": "083.gif"
                ":P": "048.gif"
                "^^": "055.gif"
                "x(": "010.gif"
                "&lt;3": "112.gif"
                ":blush:": "029.gif"
                ":evil:": "002.gif"
                "lol": "015.gif"
                ":'('": "004.gif"
                ":&gt;" : "054.gif"
                "8)" : "053.gif"
                "Oo": "031.gif"
                "oO": "031.gif"
            
        getSmiley: (key) ->
            return @path + @smileys[key]
            
        getSmileys: () ->
            return @smileys

    return Smileys
]


angular.module('WebChat').factory 'ActiveUser', () ->
    ActiveUser = 
        id: null

    ActiveUser.canHandle = (message) ->
        if message.type == 'activeuser'
        	return true
        else
        	return false

    ActiveUser.handle = (message) ->
        ActiveUser.id = message.data.id


    return ActiveUser


angular.module('WebChat').factory '_WebChatWebSocket', () ->

    class WebChatWebSocket

        constructor: ->
            @_initialized = false
            @_shelvedQueries = []
            @_callbacks = 
                onOpen: -> 
                    console.info('websocket is open')
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
                    @_initialized = true
                    @_runShelvedQueries()
                
                @_connection.onmessage = (event) =>
                    msg = event.data
                    json = JSON.parse(msg)
                    @_callbacks.onReceive(json)
                    console.info("Received: #{msg}")
                    # check if we got an error from the server
                    if json.type == 'status'
                        if json.data.level != 'ok'
                            console.warn(json.data.msg)
                
                @_connection.onclose = =>
                    @_callbacks.onClose()

                @_connection.onerror = =>
                    @_callbacks.onError()

            catch error
                console.error("Cant connect to #{url}")


        _shelveMessage: (msg) ->
            console.info 'saving message to send on connect: ' + msg
            @_shelvedQueries.push(msg)


        _runShelvedQueries: ->
            for query in @_shelvedQueries
                @send(query)
            @_shelvedQueries = []


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
            if not @_initialized
                @_shelveMessage(msg)
            else
                @_connection.send(msg)
                console.info("Sending #{msg}")


        # sends a json object to the webserver
        sendJSON: (json_object) ->
            msg = JSON.stringify(json_object)
            @send(msg)

    return WebChatWebSocket




angular.module('WebChat').factory '_FileModel', ['_Model', (_Model) ->

    class FileModel extends _Model

        constructor: () ->
            super('file')


        create: (file) ->
            file.getSize = =>
                return @kbToHumanReadable(file.size, 1)
            super(file)


        kbToHumanReadable: (bytes, precision) ->
            kilobyte = 1024
            megabyte = kilobyte * 1024;
            gigabyte = megabyte * 1024;
            terabyte = gigabyte * 1024;

            if bytes >= kilobyte and bytes < megabyte
                return (bytes / kilobyte).toFixed(precision) + ' KB'
            if bytes >= megabyte and bytes < gigabyte
                return (bytes / megabyte).toFixed(precision) + ' MB'
            else if bytes >= gigabyte and bytes < terabyte
                return (bytes / gigabyte).toFixed(precision) + ' GB'
            else if bytes >= terabyte
                return (bytes / terabyte).toFixed(precision) + ' TB'
            else
                return bytes + ' B'

    return FileModel
]


angular.module('WebChat').factory '_GroupModel', ['_Model', (_Model) ->

    class GroupModel extends _Model

        constructor: () ->
            super('group')


    return GroupModel
]


angular.module('WebChat').factory 'ChannelMessageCache', ->

    class ChannelMessageCache

        constructor: () ->
            @channels = {}
            @earliestMessage = {}

        registerChannelMessage: (item) ->
            channel = @getChannelById(item.channel_id)
            channel.push(item)
            # register earliest message
            if @earliestMessage[item.channel_id] != undefined
                if @earliestMessage[item.channel_id].date > item.date
                    @earliestMessage[item.channel_id] = item  
            else
                @earliestMessage[item.channel_id] = item


        getEarliestMessage: (channelId) ->
            return @earliestMessage[channelId]
            

        getLastMessage: (channelId) ->
            channel = @getChannelById(channelId)
            if channel.length == 0
                return null
            else
                return channel[channel.length - 1]

                
        getChannelById: (channelId) ->
            if @channels[channelId] == undefined
                @channels[channelId] = []
            return @channels[channelId]


    return new ChannelMessageCache()

angular.module('WebChat').factory '_UserModel', ['_Model', (_Model) ->

    class UserModel extends _Model

        constructor: () ->
            super('user')


        create: (user) ->
            super( @enhance(user) )


        update: (user) ->
            super( @enhance(user) )


        enhance: (user) ->
            user.getFullName = ->
                @firstname + " " + @lastname
            return user

    return UserModel
]


angular.module('WebChat').factory '_MessageModel', 
    ['_Model', 'Smileys', 'UserModel', 'ActiveUser', 'ChannelMessageCache',
     (_Model, Smileys, UserModel, ActiveUser, ChannelMessageCache) ->

        class MessageModel extends _Model

            constructor: () ->
                super('message')
                @channelCache = ChannelMessageCache
                @lastShownTimestamp = {}

            create: (item) ->              
                super( @enhance(item) )


            update: (item) ->
                super( @enhance(item) )


            delete: (item) ->
                super(item)                


            enhance: (item) ->
                # add special formatting for last message
                lastMsg = @channelCache.getLastMessage(item.channel_id)
                if lastMsg == null
                    item.showDate = true
                    item.color = 0
                    @lastShownTimestamp[item.channel_id] = item.date
                else
                    # alternate color by author
                    if lastMsg.owner_id != item.owner_id
                        item.color = (lastMsg.color + 1) % 2
                    else 
                        item.color = lastMsg.color

                    # show timestamps when last message is more than a minute
                    # ago
                    minuteNow = new Date(item.date);
                    minuteBefore = new Date(@lastShownTimestamp[item.channel_id])
                    minutesPassed = (item.date - @lastShownTimestamp[item.channel_id])/60000
                    if minutesPassed >= 1 or minuteNow.getMinutes() != minuteBefore.getMinutes()
                        item.showDate = true
                        @lastShownTimestamp[item.channel_id] = item.date
                    else
                        item.showDate = false 

                @channelCache.registerChannelMessage(item)

                # only enhance text messages
                if item.type == 'text'
                
                    # check if we were hightlighted
                    user = UserModel.getItemById(ActiveUser.id)
                    highlightName = user.firstname + user.lastname
                    if item.message.indexOf(highlightName) != -1
                        item.highlighted = true
                        document.getElementById('sounds').play()
                    else
                        item.highlighted = false

                    item.message = @cleanXSS(item.message)
                    item.message = @sugarText(item.message)
                    item.message = @convertNewLines(item.message)

                return item


            cleanXSS: (text) ->
                return $('<div>').text(text).html();


            # wraps links in <a> tags, pictures in <img> tags
            # msg: the message we search
            # return: the final message
            sugarText: (msg) ->
                # first place all urls in <a> tags
                msg = @createLinks(msg)
                
                # add smileys
                smileys = Smileys
                for key, smile of smileys.getSmileys()
                    img = '<img width="50" height="50" alt="' + key + '" src="' + smileys.getSmiley(key) + '" />'
                    middle_line_regex = new RegExp("(" + @escapeForRegex(key) + ")([\.\?!,;]*) ", "g")
                    msg = msg.replace(middle_line_regex, " " + img + "$2 ")
                    break_line_regex = new RegExp(@escapeForRegex(key) + "([\.\?!,;]*)<br", "g")
                    msg = msg.replace(break_line_regex, img + '$1<br')
                    new_line_regex = new RegExp(@escapeForRegex(key) + "([\.\?!,;]*)\\n", "g")
                    msg = msg.replace(new_line_regex, img + '$1\n')
                    end_line_regex = new RegExp( "(.*)" + @escapeForRegex(key) + "([\.\?!,;]*)$", "g")
                    msg = msg.replace(end_line_regex, "$1" + img + "$2")
                    start_line_regex = new RegExp( "^" + @escapeForRegex(key) + "([\.\?!,;]*)(.*)$", "g")
                    msg = msg.replace(start_line_regex, img + "$1$2")

                # now replace all images in <a> tags with <img> tags
                pictures = ["png", "jpg", "jpeg", "gif"]
                for pic in pictures
                    # put a br before and after the image
                    pic_regex = new RegExp('<a href="(.*\.' + @escapeForRegex(pic) + ')">(.*)<\/a>', "gim")
                    msg = msg.replace(pic_regex, '<br/><a href="$1"><img alt="$1" src="$1" /></a><br/>')
                
                # replace youtube links with embedded video
                yt_regex = /<a href=".*youtube.com\/watch\?v=([0-9a-zA-Z_-]{11}).*">.*<\/a>/gi
                msg = msg.replace(yt_regex, '<br/><iframe width="560" height="315" src="http://www.youtube.com/embed/$1" frameborder="0" allowfullscreen></iframe><br/>')
                return msg
                

            # replaces links and emails with html links
            createLinks:(msg) ->
                url_regex = /\b((?:https?|ftp):\/\/[a-z0-9+&@#\/%?=~_|!:,.;-]*[a-z0-9+&@#\/%=~_|-])/gi
                pseudo_url_regex = /(^|[^\/])(www\.[\S]+(\b|$))/gi
                email_regex = /\w+@[a-zA-Z_]+?(?:\.[a-zA-Z]{2,6})+/gi
                msg = msg.replace(url_regex, '<a href="$1">$1</a>')
                msg = msg.replace(pseudo_url_regex, '$1<a target="_blank" href="http://$2">$2</a>')
                msg = msg.replace(email_regex, '<a href="mailto:$&">$&</a>')
                return msg


            escapeForRegex: (text) ->
                return (text+'').replace(/([.?*+^$[\]\\(){}|-])/g, "\\$1")


            convertNewLines: (text) ->
                return text.replace('\n', '<br />')



        return MessageModel
    ]


angular.module('WebChat').factory '_ChannelModel', ['_Model', (_Model) ->

    class ChannelModel extends _Model

        constructor: () ->
            super('channel')

        create: (item) ->
            item.autoScroll = true
            super(item)

    return ChannelModel
]


angular.module('WebChat').factory '_Model', ['$rootScope', ($rootScope) ->

    Model = class

        constructor: (@modelType) ->
            @items = []
            @hashMap = {} # hashmap for quick access via item id
            $rootScope.$on 'message', (scope, message) =>
                console.log @
                if @canHandle(message.type)
                    
                        @handle message



        handle: (message) ->
            $rootScope.$apply =>
                switch message.action
                    when 'create' then @create(message.data)
                    when 'update' then @update(message.data)
                    when 'delete' then @delete(message.data)


        create: (item) ->
            if @hashMap[item.id] != undefined
                @update(item)
            else
                @hashMap[item.id] = item
                @items.push(item)


        update: (updatedItem) ->
            for item, counter in @items
                if item.id == updatedItem.id
                    @items[counter] = updatedItem;


        delete: (removedItem) ->
            removeItemId = -1
            for item, counter in @items
                if item.id == removedItem.id
                    removeItemId = counter
            if removeItemId >= 0
                @items.splice(removeItemId, 1)
                delete @hashMap[removedItemId]


        getItemById: (id) ->
            return @hashMap[id]

        getItems: ->
            return @items


        canHandle: (message) ->
            if message.type == @modelType
                return true
            else
                return false

    return Model

]

angular.module('WebChat').factory '_MimeTypes', ->

    class MimeTypes

        constructor: (@path="/assets/images/mimetypes/") ->
            

        getIconPath: (key) ->
            key = key.replace('/', '-')
            fullPath = @path + key + '.png'
            return fullPath


    return MimeTypes


angular.module('WebChat').factory '_InviteUserMessage', ['_Message', (_Message)->

    class InviteUserMessage extends _Message

        constructor: (@userId, @channelId, @value) ->
            super('inviteuser')


        serialize: ->
            data = 
                id: @channelId
                users: @userId
                value: @value
            return super(data)


    return InviteUserMessage

]

angular.module('WebChat').factory '_PingMessage', ['_Message', (_Message)->

    class PingMessage extends _Message

        constructor: () ->
            super('ping')


        serialize: ->
            data = {}
            return super(data)            


    return PingMessage

]

angular.module('WebChat').factory '_ModUserMessage', ['_Message', (_Message)->

    class ModUserMessage extends _Message

        constructor: (@userId, @channelId, @value) ->
            super('moduser')


        serialize: ->
            data = 
                channel_id: @channelId
                user_id: @userId
                value: @value
            return super(data)


    return ModUserMessage

]

angular.module('WebChat').factory '_LoadMessages', ['_Message', (_Message)->

    class LoadMessages extends _Message
    
        constructor: (@channelId, @messageId, @earliestTimestamp) ->
            super('loadmessages')


        serialize: ->
            data = 
                channel_id: @channelId
                message_id: @messageId
                earliest_timestamp: @earliestTimestamp
            return super(data)         


    return LoadMessages

]

angular.module('WebChat').factory '_ReadonlyUserMessage', ['_Message', (_Message)->

    class ReadonlyUserMessage extends _Message
    
        constructor: (@userId, @channelId, @value) ->
            super('readonlyuser')


        serialize: ->
            data = 
                channel_id: @channelId
                user_id: @userId
                value: @value
            return super(data)     


    return ReadonlyUserMessage

]

angular.module('WebChat').factory '_NewChannelMessage', ['_Message', (_Message)->

    class NewChannelMessage extends _Message

        constructor: (@name, @topic, @isPublic) ->
            super('newchannel')


        serialize: ->
            data = 
                name: @name
                topic: @topic
                is_public: @isPublic
            return super(data)


    return NewChannelMessage

]

angular.module('WebChat').factory '_Message', () ->

    Message = class

        constructor: (@type) ->


        serialize: (data) ->
            message =
                type: @type
                data: data
            return message

    return Message


angular.module('WebChat').factory '_InviteGroupMessage', ['_Message', (_Message)->

    class InviteGroupMessage extends _Message

        constructor: (@groupId, @channelId, @value) ->
            super('invitegroup')


        serialize: ->
            data = 
                id: @channelId
                groups: @groupId
                value: @value
            return super(data)


    return InviteGroupMessage
]

angular.module('WebChat').factory '_JoinMessage', ['_Message', (_Message)->

    class JoinMessage extends _Message

        constructor: (@id) ->
            super('join')


        serialize: ->
            data = 
                id: @id
            return super(data)


    return JoinMessage

]

angular.module('WebChat').factory '_ReadonlyGroupMessage', ['_Message', (_Message)->

    class ReadonlyGroupMessage extends _Message
    
        constructor: (@groupId, @channelId, @value) ->
            super('readonlygroup')


        serialize: ->
            data = 
                channel_id: @channelId
                user_id: @groupId
                value: @value
            return super(data)         


    return ReadonlyGroupMessage

]

angular.module('WebChat').factory '_DeleteFileMessage', ['_Message', (_Message)->

    class DeleteFileMessage extends _Message

        constructor: (@fileId) ->
            super('filedelete')


        serialize: ->
            data = 
                id: @fileId
            return super(data)


    return DeleteFileMessage
]

angular.module('WebChat').factory '_ModGroupMessage', ['_Message', (_Message)->

    class ModGroupMessage extends _Message

        constructor: (@groupId, @channelId, @value) ->
            super('modgroup')


        serialize: ->
            data = 
                channel_id: @channelId
                user_id: @groupId
                value: @value
            return super(data)


    return ModGroupMessage

]

angular.module('WebChat').factory '_SendMessage', ['_Message', (_Message)->

    class SendMessage extends _Message    

        constructor: (@text, @messageType, @channelId) ->
            super('message')


        serialize: ->
            data = 
                'message': @text
                'type': @messageType
                'channel_id': @channelId
            return super(data)            


    return SendMessage

]

$(document).ready ->



pingTimout = true

angular.module('WebChat').directive 'activity',
['_PingMessage', 'WebChatWebSocket', 'PING_TIMEOUT',
(_PingMessage, WebChatWebSocket, PING_TIMEOUT) ->

    ping = ->
        if pingTimout
            pingTimout = false
            message = new _PingMessage()
            WebChatWebSocket.sendJSON(message.serialize())
            setTimeout ->
                pingTimout = true
            , PING_TIMEOUT

    return (scope, elm, attr) ->

        $(elm).keyup ->
            ping()
            scope.$apply attr.activity;

        $(elm).mousemove ->
            ping()
            scope.$apply attr.activity;

        $(elm).click ->
            ping()
            scope.$apply attr.activity;
]



angular.module('WebChat').directive 'autoScroll',
['ChannelModel', 'ActiveChannel',
(ChannelModel, ActiveChannel) ->

    autoScrollDirective = 
        restrict: 'A'
        link: (scope, elm, attr) ->
            channel = ChannelModel.getItemById(ActiveChannel.getActiveChannelId())
            if channel.autoScroll
                $elem = $(elm)
                if $elem.hasClass('line')
                    stream = $elem.parent().parent().parent()
                else
                    stream = $elem.parent()
                stream.scrollTop(stream.prop("scrollHeight"))

    return autoScrollDirective
]



angular.module('WebChat').directive 'whenScrolled',
['ChannelModel', 'ActiveChannel', '_LoadMessages', 'WebChatWebSocket', 'ChannelMessageCache',
(ChannelModel, ActiveChannel, _LoadMessages, WebChatWebSocket, ChannelMessageCache) ->

    return (scope, elm, attr) ->

        elm.bind 'scroll', ->
            stream = $(elm)
            channelId = ActiveChannel.getActiveChannelId()
            channel = ChannelModel.getItemById(channelId)
            if (stream.innerHeight() + stream.scrollTop()) >= stream.prop("scrollHeight")
                channel.autoScroll = true
            else
                channel.autoScroll = false
                channel.scrollTop = stream.prop("scrollHeight")

            if stream.scrollTop() <= 0
                earliestMessage = ChannelMessageCache.getEarliestMessage(channelId)
                msg = new _LoadMessages(channelId, earliestMessage.id, earliestMessage.date)
                WebChatWebSocket.sendJSON(msg.serialize())

            scope.$apply attr.whenScrolled;

]



angular.module('WebChat').factory '_DialogueController', ['_Controller', (_Controller) ->
    
    class DialogueController extends _Controller

        constructor: ($scope) ->
            super($scope)

            $scope.showNewChannelDialogue = (show) =>
                $scope.newChannelDialogue = show

            $scope.setNewChannelDialogue = (show) =>
                $scope.newChannelDialogue = show

    return DialogueController

]

angular.module('WebChat').factory '_GroupListController', 
    ['_Controller', '_InviteUserMessage', '_InviteGroupMessage', 'GroupModel', 
     'UserModel',
     (_Controller, _InviteUserMessage, _InviteGroupMessage, GroupModel, UserModel) ->

        class GroupListController extends _Controller

            constructor: ($scope) ->
                super($scope)

                @groupmodel = GroupModel
                @usermodel = UserModel

                $scope.getGroups = =>
                    return @groupmodel.getItems()
                $scope.getUsers = =>
                    return @usermodel.getItems()

                $scope.inviteUser = (userId, value) =>
                    @simpleChannelMessage(userId, _InviteUserMessage, value)

                $scope.inviteGroup = (groupId, value) =>
                    @simpleChannelMessage(groupId, _InviteGroupMessage, value)

        return GroupListController

    ]

angular.module('WebChat').factory '_Controller', 
    ['_SendMessage', 'WebChatWebSocket', 'ActiveChannel', 
     (_SendMessage, WebChatWebSocket, ActiveChannel) ->

        class Controller

            constructor: ($scope) ->
                @getActiveChannelId = =>
                    return ActiveChannel.getActiveChannelId()
                @setActiveChannelId = (id) =>
                    ActiveChannel.setActiveChannelId(id)

                @simpleChannelMessage = (id, Msg, value) =>
                    activeChannelId = @getActiveChannelId()
                    if activeChannelId != null
                        message = new Msg(id, activeChannelId, value)
                        @sendMessage(message)

                @sendMessage = (Msg) =>
                    WebChatWebSocket.sendJSON(Msg.serialize())

                $scope.getActiveChannelId = =>
                    return @getActiveChannelId()

        return Controller

    ]

angular.module('WebChat').factory '_ChannelListController', 
    ['_Controller', '_JoinMessage', 'ChannelModel',
     (_Controller, _JoinMessage, ChannelModel) ->

        class ChannelListController extends _Controller

            constructor: ($scope) ->
                super($scope)
            
                @channelmodel = ChannelModel

                $scope.getChannels = =>
                    return @channelmodel.getItems()

                $scope.join = (id) =>
                    message = new _JoinMessage(id)
                    @sendMessage(message)
                    @setActiveChannelId(id)
                    $scope.selected = id
                    $("#input_field").focus()


        return ChannelListController

    ]

angular.module('WebChat').controller 'ChannelListController', 
    ['$scope', '_ChannelListController', 
     ($scope, _ChannelListController) -> 
        return new _ChannelListController($scope)
    ]

angular.module('WebChat').controller 'GroupListController', 
    ['$scope', '_GroupListController',
     ($scope, _GroupListController) -> 
        return new _GroupListController($scope)
    ]

angular.module('WebChat').controller 'MessageController', 
    ['$scope', '_MessageController',
     ($scope, _MessageController) -> 
        return new _MessageController($scope)
    ]

angular.module('WebChat').controller 'GroupsInChannelController', 
    ['$scope', '_GroupsInChannelController',
     ($scope, _GroupsInChannelController) -> 
        return new _GroupsInChannelController($scope)
    ]

angular.module('WebChat').controller 'FilesInChannelController', 
    ['$scope', '_FilesInChannelController', 
     ($scope, _FilesInChannelController) -> 
        return new _FilesInChannelController($scope)
    ]

angular.module('WebChat').controller 'DialogueController', 
    ['$scope', '_DialogueController', 
    ($scope, _DialogueController) -> 
        return new _DialogueController($scope)
    ]

angular.module('WebChat').controller 'NewChannelController', 
    ['$scope', '_NewChannelController', 
    ($scope, _NewChannelController) -> 
        return new _NewChannelController($scope)
    ]

angular.module('WebChat').factory '_GroupsInChannelController', 
    ['_Controller', '_ModUserMessage', '_ModGroupMessage', '_ReadonlyUserMessage', 
     '_ReadonlyGroupMessage', '_InviteGroupMessage', '_InviteUserMessage', 
     'ChannelModel', 'GroupModel', 'UserModel',
     (_Controller, _ModUserMessage, _ModGroupMessage, _ReadonlyUserMessage, 
      _ReadonlyGroupMessage, _InviteGroupMessage, _InviteUserMessage, ChannelModel, 
      GroupModel, UserModel) ->

        class GroupsInChannelController extends _Controller

            constructor: ($scope) ->
                super($scope)

                @channelmodel = ChannelModel
                @groupmodel = GroupModel
                @usermodel = UserModel

                $scope.getGroups = =>
                    return @groupmodel.getItems()
                $scope.getUsers = =>
                    return @usermodel.getItems()

                $scope.getActiveChannel = () =>
                    if @getActiveChannelId() != null
                        return @channelmodel.getItemById(@getActiveChannelId())
                    else
                        return null

                $scope.inviteUser = (userId, value) =>
                    @simpleChannelMessage(userId, _InviteUserMessage, value)

                $scope.inviteGroup = (groupId, value) =>
                    @simpleChannelMessage(groupId, _InviteGroupMessage, value)

                $scope.modUser = (userId, value) =>
                    @simpleChannelMessage(userId, _ModUserMessage, value)

                $scope.modGroup = (groupId, value) =>
                    @simpleChannelMessage(groupId, _ModGroupMessage, value)

                $scope.readonlyUser = (userId, value) =>
                    @simpleChannelMessage(userId, _ReadonlyUserMessage, value)

                $scope.readonlyGroup = (groupId, value) =>
                    @simpleChannelMessage(groupId, _ReadonlyGroupMessage, value)


        return GroupsInChannelController

    ]

angular.module('WebChat').factory '_NewChannelController', 
    ['_Controller', '_NewChannelMessage', (_Controller, _NewChannelMessage) ->
        
        class NewChannelController extends _Controller

            constructor: ($scope) ->
                super($scope)                
                @resetInput($scope)

                $scope.createNewChannel = (name, topic, isPublic) =>
                    @resetInput($scope)
                    message = new _NewChannelMessage(name, topic, isPublic)
                    @sendMessage(message)
                    $scope.setNewChannelDialogue(false)


            resetInput: ($scope) ->
                $scope.newChannelName = ''
                $scope.newChannelTopic = ''
                $scope.newChannelPublic = false

        return NewChannelController

    ]

angular.module('WebChat').factory '_MessageController', 
    ['_Controller', '_SendMessage', 'GroupModel', 'UserModel', 'MessageModel',
     'ChannelModel', '$filter'
     (_Controller, _SendMessage, GroupModel, UserModel, MessageModel, 
      ChannelModel, $filter) ->

        class MessageController extends _Controller

            constructor: ($scope) ->
                super($scope)

                $("#input_field").keydown (e) =>
                    if e.keyCode == 9 # tab
                        @autoComplete($scope)
                        return false
                    if e.keyCode == 13 and not e.shiftKey # enter
                        $("#input_send").trigger('click')
                        return false

                @resetInput($scope)

                @groupmodel = GroupModel
                @usermodel = UserModel
                @messagemodel = MessageModel
                @channelmodel = ChannelModel

                $scope.getChannels = =>
                    return @channelmodel.getItems()
                $scope.getUsers = =>
                    return @usermodel.getItems()
                $scope.getGroups = =>
                    return @groupmodel.getItems()
                $scope.getMessages = =>
                    return @messagemodel.getItems()

                $scope.getUserFullName = (userId) =>
                    user = @usermodel.getItemById(userId)
                    return user.getFullName()

                $scope.sendInput = (text, messageType, channelId) =>
                    if text != ''
                        message = new _SendMessage(text, messageType, channelId)
                        @sendMessage(message)
                        @resetInput($scope)


            resetInput: ($scope) ->
                $scope.messageType = 'text'
                $scope.textInput = ''


            autoComplete: ($scope) ->
                # deconstruct the sentence plus the part we want to autocomplete
                toComplete = ''
                toAppend = ''
                if $scope.textInput.length == 0
                    return
                else
                    words = $scope.textInput.split(' ')
                    toComplete = words[words.length - 1]
                    if words.length >= 2
                        words.pop()  # remove the last word that we want to complete
                        toAppend = words.join(' ')
                        toAppend +=  ' '

                # loop through all users in the channel to see if we can 
                # autocomplete
                userIds = {}
                channel = @channelmodel.getItemById(@getActiveChannelId())
                users = @usermodel.getItems()

                for userId in channel.users
                    userIds[userId] = true

                userInGroupFilter = $filter('userInGroup')
                for groupId in channel.groups
                    for user in userInGroupFilter(users, groupId)
                        userIds[user.id] = true

                for userId, bool of userIds
                    user = @usermodel.getItemById(userId)
                    fullName = user.firstname + user.lastname
                    if fullName.toLowerCase().indexOf(toComplete.toLowerCase()) == 0
                        $scope.$apply -> 
                            $scope.textInput = toAppend + fullName


        return MessageController

    ]

angular.module('WebChat').factory '_FilesInChannelController', 
    ['_Controller', '_DeleteFileMessage', 'FileModel', 'MimeTypes', 'ChannelModel',
     (_Controller, _DeleteFileMessage, FileModel, MimeTypes, ChannelModel) ->

        class FilesInChannelController extends _Controller

            constructor: ($scope) ->
                super($scope)

                @filemodel = FileModel
                @channelmodel = ChannelModel

                $scope.getFiles = =>
                    return @filemodel.getItems()

                $scope.getActiveChannel = () =>
                    if @getActiveChannelId() != null
                        return @channelmodel.getItemById(@getActiveChannelId())
                    else
                        return null

                $scope.getMimeTypeImage = (fileId) =>
                    file = @filemodel.getItemById(fileId)
                    if file != undefined
                        mime = MimeTypes.getIconPath(file.mimetype)
                        css = 'background-image: url("' + mime + '")'
                        return css
                        
                $scope.deleteFile = (fileId) =>
                    message = new _DeleteFileMessage(fileId)
                    @sendMessage(message)


        return FilesInChannelController

    ]
    

angular.module('WebChat').filter 'userInGroup', ->
    return (users, groupId) ->
        result = []
        for user in users
            for userGroupId in user.groups
                if userGroupId == groupId
                    result.push(user)
        return result

angular.module('WebChat').filter 'userInChannel', ->
    return (users, channel) ->
        result = []
        if channel == undefined or channel == null or channel.users == undefined
            return result
        for user in users
            if user.id in channel.users
                result.push(user)
        return result

angular.module('WebChat').filter 'fileInChannel', ->
    return (files, channel) ->
        result = []
        if channel == undefined or channel == null or channel.files == undefined
            return result
        for file in files
            if file.id in channel.files
                result.push(file)
        return result

angular.module('WebChat').filter 'groupInChannel', ->
    return (groups, channel) ->
        result = []
        if channel == undefined or channel == null or channel.groups == undefined
            return result
        for group in groups
            if group.id in channel.groups
                result.push(group)
        return result

angular.module('WebChat').filter 'highlight', ->
    return (messages) ->
        SyntaxHighlighter.highlight()
        return messages

angular.module('WebChat').filter 'messageInChannel', ->
    return (messages, channelId) ->
        result = []
        for message in messages
            if message.channel_id == channelId
                result.push(message)
        return result

angular.module('WebChat').filter 'messageInChannel', ->
    return (messages, channelId) ->
        result = []
        for message in messages
            if message.channel_id == channelId
                result.push(message)
        return result