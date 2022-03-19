##################################################################################
# Will not fix:                                                                  #
#   1. It can't handle files with pages of different dimensions well.            #
#   2. [Files App] When the file is processed, preexisting annotations are lost. #
##################################################################################

from PyPDF3 import PdfFileWriter, PdfFileReader
from PyPDF3.pdf import PageObject
import os

#################
# Customization #
by_row = True
#################

input_pathway = input('Drag the file into Terminal.\n').replace('\\','')[:-1]
input_file = PdfFileReader(open(input_pathway, 'rb'), strict=False)
output_file = PdfFileWriter()

input_page_width = input_file.getPage(0).mediaBox.upperRight[0]
input_page_height = input_file.getPage(0).mediaBox.upperRight[1]

# 2 pages (documents)
if input_page_width < input_page_height:
    output_page_width = 2*input_page_width
    output_page_height = input_page_height
    for i in range(0, input_file.getNumPages(), 2): # For every output page
        curr_page = PageObject.createBlankPage(None, output_page_width, output_page_height)
        curr_page.mergePage(input_file.getPage(i))
        try:
            curr_page.mergeTranslatedPage(input_file.getPage(i+1), input_page_width, 0)
        except:
            pass
        output_file.addPage(curr_page)

# 4 pages (slides)
else:
    output_page_width = 2*input_page_width
    output_page_height = 2*input_page_height
    for i in range(0, input_file.getNumPages(), 4): # For every output page
        curr_page = PageObject.createBlankPage(None, output_page_width, output_page_height)
        curr_page.mergeTranslatedPage(input_file.getPage(i), 0, input_page_height)
        try:
            if by_row:
                curr_page.mergeTranslatedPage(input_file.getPage(i+1), input_page_width, input_page_height)
            else:
                curr_page.mergeTranslatedPage(input_file.getPage(i+1), 0, 0)
        except:
            pass
        try:
            if by_row:
                curr_page.mergeTranslatedPage(input_file.getPage(i+2), 0, 0)
            else:
                curr_page.mergeTranslatedPage(input_file.getPage(i+2), input_page_width, input_page_height)
        except:
            pass
        try:
            curr_page.mergeTranslatedPage(input_file.getPage(i+3), input_page_width, 0)
        except:
            pass
        output_file.addPage(curr_page)

input_pathway = os.path.split(input_pathway)
if input_pathway[0]:
    output_file.write(open(input_pathway[0]+'/shrink_'+input_pathway[1], 'wb'))
else:
    output_file.write(open('shrink_'+input_pathway[1], 'wb'))
