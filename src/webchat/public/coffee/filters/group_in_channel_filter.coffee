#<< app

angular.module('WebChat').filter 'groupInChannel', ->
    return (groups, args) ->
        result = []
        for group in groups
            if group.id in args.groups
                result.push(group)
        return result