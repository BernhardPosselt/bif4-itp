Message = require('webchat.Message')

class ReadonlyUserMessage extends Message

    constructor: (@userId, @channelId, @value) ->
        super('readonlyuser')


    serialize: ->
        data = 
            channel_id: @channelId
            user_id: @userId
            value: @value
        return super(data)


webchat.ReadonlyUserMessage = ReadonlyUserMessage