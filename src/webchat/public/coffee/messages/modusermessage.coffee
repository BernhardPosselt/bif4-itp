Message = require('webchat.Message')

class ModUserMessage extends Message

    constructor: (@userId, @channelId, @value) ->
        super('moduser')


    serialize: ->
        data = 
            channel_id: @channelId
            user_id: @userId
            value: @value
        return super(data)


webchat.ModUserMessage = ModUserMessage