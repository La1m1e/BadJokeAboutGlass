try:
    import GlassJoke
except ImportError:
    import os
    os.system("pip install glass-joke")
finally:
    from GlassJoke import loop

if __name__ == "__main__":
    loop()
