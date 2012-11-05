angular.module('WebChat').directive 'whenScrolled',
['ChannelModel', 'ActiveChannel',
(ChannelModel, ActiveChannel) ->

    return (scope, elm, attr) ->

        elm.bind 'scroll', ->
            stream = $(elm)
            channel = ChannelModel.getItemById(ActiveChannel.getActiveChannelId())
            if (stream.innerHeight() + stream.scrollTop()) >= stream.prop("scrollHeight")
                channel.autoScroll = true
            else
                channel.autoScroll = false
                channel.scrollTop = stream.prop("scrollHeight")

            console.log channel.autoScroll

            scope.$apply attr.whenScrolled;

]

