Message = require('webchat.Message')

class ReadonlyGroupMessage extends Message

    constructor: (@groupId, @channelId, @value) ->
        super('readonlygroup')


    serialize: ->
        data = 
            channel_id: @channelId
            user_id: @groupId
            value: @value
        return super(data)


webchat.ReadonlyGroupMessage = ReadonlyGroupMessage