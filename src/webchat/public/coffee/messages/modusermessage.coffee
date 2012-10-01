window.WebChat or= {}

class ModUserMessage extends window.WebChat.Message

    constructor: (@userId, @channelId, @value) ->
        super('moduser')


    serialize: ->
        data = 
            channel_id: @channelId
            user_id: @userId
            value: @value
        return super(data)


window.WebChat.ModUserMessage = ModUserMessage