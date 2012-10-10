angular.module('WebChat').factory '_MessageModel', ['_Model', (_Model) ->

    class MessageModel extends _Model

        constructor: () ->
            super('message')

        create: (item) ->
            
            super(item)


    return MessageModel
]
