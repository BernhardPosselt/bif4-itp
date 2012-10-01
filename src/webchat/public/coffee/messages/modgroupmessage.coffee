window.WebChat or= {}

class ModGroupMessage extends window.WebChat.Message

    constructor: (@groupId, @channelId, @value) ->
        super('modgroup')


    serialize: ->
        data = 
            channel_id: @channelId
            user_id: @groupId
            value: @value
        return super(data)


window.WebChat.ModGroupMessage = ModGroupMessage