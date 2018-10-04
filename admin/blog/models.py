from django.db import models
from django.utils import timezone

DIFFICULTY_CHOICES = (
    ('1','1'),
    ('2', '2'),
    ('3','3'),
    ('4','4'),
    ('5','5'),
)
class Park(models.Model):
    author = models.ForeignKey('auth.User', on_delete=models.CASCADE)
    park_name = models.CharField(max_length=200)
    X_coordinate = models.CharField(max_length=7, default=0)
    Y_coordinate = models.CharField(max_length=7, default=0)
    Description = models.TextField()
    created_date = models.DateTimeField(
            default=timezone.now)
    published_date = models.DateTimeField(
            blank=True, null=True)

    def publish(self):
        self.published_date = timezone.now()
        self.save()
        print("okai")

    def __str__(self):
        return self.park_name

class Route(models.Model):
    author = models.ForeignKey('auth.User', on_delete=models.CASCADE)
    route_name = models.CharField(max_length=200)
    Description = models.TextField()
    created_date = models.DateTimeField(
            default=timezone.now)
    published_date = models.DateTimeField(
            blank=True, null=True)

    def publish(self):
        self.published_date = timezone.now()
        self.save()

    def __str__(self):
        return self.route_name

class Walks(models.Model):
    author = models.ForeignKey('auth.User', on_delete=models.CASCADE)
    walk_name = models.CharField(max_length=200)
    tag = models.CharField(max_length=200)
    Description = models.TextField()
    difficulty = models.CharField(max_length=6, choices=DIFFICULTY_CHOICES, default='1')
    walk_name = models.CharField(max_length=200)
    created_date = models.DateTimeField(
            default=timezone.now)
    published_date = models.DateTimeField(
            blank=True, null=True)

    def publish(self):
        self.published_date = timezone.now()
        self.save()

    def __str__(self):
        return self.walk_name
    def _tag_(self):
        return self.tag
    
class Contact(models.Model):
    author = models.ForeignKey('auth.User', on_delete=models.CASCADE)
    contact_name = models.CharField(max_length=200)
    contact_number = models.CharField(max_length=200)
    contact_emailaddress = models.CharField(max_length=200)
    Description = models.TextField()
    created_date = models.DateTimeField(
            default=timezone.now)
    published_date = models.DateTimeField(
            blank=True, null=True)

    def publish(self):
        self.published_date = timezone.now()
        self.save()

    def __str__(self):
        return self.contact_name

class Image(models.Model):
   name = models.CharField(max_length = 50)
   picture = models.ImageField(upload_to = 'pictures')

   class Meta:
      db_table = "image"

