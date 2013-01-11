angular.module('WebChat').filter 'highlight', ->
    return (messages) ->
        SyntaxHighlighter.highlight()
        return messages