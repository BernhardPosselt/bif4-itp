angular.module('WebChat').factory '_GroupModel', ['_Model', (_Model) ->

    class GroupModel extends _Model

        constructor: () ->
            super('group')
            # @create { id: 1, name: 'channel', groups: [0], users: [0, 1]}


    return GroupModel
]
