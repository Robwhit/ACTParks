from blog.forms import ImageForm
from blog.models import Image

def SaveProfile(request):
   saved = False
   
   if request.method == "POST":
      #Get the posted form
      MyImageForm = ImageForm(request.POST, request.FILES)
      
      if MyImageForm.is_valid():
         image = Profile()
         image.name = MyImageForm.cleaned_data["name"]
         image.picture = MyImageForm.cleaned_data["picture"]
         image.save()
         saved = True
   else:
      MyImageForm = Imageform()
		
   return render(request, 'saved.html', locals())

class CreateMyModelView(CreateView):
    model = Walks
    form_class = WalksForm
    template_name = 'blog/template.html'
    success_url = 'blog/success.html'
