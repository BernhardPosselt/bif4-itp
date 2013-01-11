angular.module('WebChat').filter 'messageInChannel', ->
    return (messages, channelId) ->
        result = []
        for message in messages
            if message.channel_id == channelId
                result.push(message)
        return result