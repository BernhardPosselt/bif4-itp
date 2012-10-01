window.WebChat or= {}

class ReadonlyGroupMessage extends window.WebChat.Message

    constructor: (@groupId, @channelId, @value) ->
        super('readonlygroup')


    serialize: ->
        data = 
            channel_id: @channelId
            user_id: @groupId
            value: @value
        return super(data)


window.WebChat.ReadonlyGroupMessage = ReadonlyGroupMessage