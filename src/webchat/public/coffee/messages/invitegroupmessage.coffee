angular.module('WebChat').factory '_InviteGroupMessage', ['_Message', (_Message)->

    class InviteGroupMessage extends _Message

        constructor: (@groupId, @channelId, @value) ->
            super('invitegroup')


        serialize: ->
            data = 
                id: @channelId
                group: @groupId
                value: @value
            return super(data)


    return InviteGroupMessage
]