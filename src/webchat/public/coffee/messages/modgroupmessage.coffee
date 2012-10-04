Message = require('webchat.Message')

class ModGroupMessage extends Message

    constructor: (@groupId, @channelId, @value) ->
        super('modgroup')


    serialize: ->
        data = 
            channel_id: @channelId
            user_id: @groupId
            value: @value
        return super(data)


webchat.ModGroupMessage = ModGroupMessage