window.WebChat or= {}

class InviteUserMessage extends window.WebChat.Message

    constructor: (@userId, @channelId) ->
        super('inviteuser')


    serialize: ->
        data = 
            channel_id: @channelId
            user_id: @userId
        return super(data)


window.WebChat.InviteUserMessage = InviteUserMessage