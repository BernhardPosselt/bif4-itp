Message = require('webchat.Message')

class InviteGroupMessage extends Message

    constructor: (@groupId, @channelId, @value) ->
        super('invitegroup')


    serialize: ->
        data = 
            channel_id: @channelId
            group_id: @groupId
            value: @value
        return super(data)


webchat.InviteGroupMessage = InviteGroupMessage