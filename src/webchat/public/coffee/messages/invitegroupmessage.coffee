window.WebChat or= {}

class InviteGroupMessage extends window.WebChat.Message

    constructor: (@groupId, @channelId) ->
        super('invitegroup')


    serialize: ->
        data = 
            channel_id: @channelId
            group_id: @groupId
        return super(data)


window.WebChat.InviteGroupMessage = InviteGroupMessage