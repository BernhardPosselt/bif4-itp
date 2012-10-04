#<<messages/message

class InviteUserMessage extends Message

    constructor: (@userId, @channelId, @value) ->
        super('inviteuser')


    serialize: ->
        data = 
            channel_id: @channelId
            user_id: @userId
            value: @value
        return super(data)
