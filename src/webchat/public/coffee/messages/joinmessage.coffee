window.WebChat or= {}

class JoinMessage extends window.WebChat.Message

    constructor: (@id) ->
        super('join')


    serialize: ->
        data = 
            id: @id
        return super(data)


window.WebChat.JoinMessage = JoinMessage