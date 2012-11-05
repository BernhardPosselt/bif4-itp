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