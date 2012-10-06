angular.module('WebChat').factory '_MessageModel', ['_Model', (_Model) ->

    class MessageModel extends _Model

        constructor: () ->
            super('message')
            # @create { id: 1, name: 'channel', groups: [0], users: [0, 1]}


    return MessageModel
]
