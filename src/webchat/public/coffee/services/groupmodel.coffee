angular.module('WebChat').factory '_GroupModel', ['_Model', (_Model) ->

    class GroupModel extends _Model

        constructor: () ->
            super('group')


    return GroupModel
]
