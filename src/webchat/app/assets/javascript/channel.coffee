###
This class represents one channel item
###

class Channel
    
  constructor: () ->
    @channels = $ "#channels ul"

    # values
    @id = 0
    @caption = ""
    
  instance: (@id) ->
    @dom_elem = @channels.children("li") # FIXME

  create: () ->
  # insert and the element to the channel at a given position
  # position: the position where it should be added
    @dom_elem = $ "<li>"
    @dom_elem.append $ "<a href=''>#{@caption}</a>"
    @channels.append @dom_elem


