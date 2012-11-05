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
                msg = new _LoadMessages(channelId, earliestMessage.id earliestMessage.date)
                WebChatWebSocket.sendJSON(msg.serialize())

            scope.$apply attr.whenScrolled;

]

