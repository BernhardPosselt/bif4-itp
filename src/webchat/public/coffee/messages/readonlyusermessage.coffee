window.WebChat or= {}

class ReadonlyUserMessage extends window.WebChat.Message

    constructor: (@userId, @channelId, @value) ->
        super('readonlyuser')


    serialize: ->
        data = 
            channel_id: @channelId
            user_id: @userId
            value: @value
        return super(data)


window.WebChat.ReadonlyUserMessage = ReadonlyUserMessage